package iium.jjs.sansang_back.config;


import iium.jjs.sansang_back.jwt.JwtAccessDeniedHandler;
import iium.jjs.sansang_back.jwt.JwtAuthenticationEntryPoint;
import iium.jjs.sansang_back.jwt.JwtFilter;
import iium.jjs.sansang_back.jwt.TokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {

    private final TokenProvider tokenProvider;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer(){
        return (web) -> web.ignoring()
                .antMatchers("/css/**", "/js/**","/images","/lib/**","/profile/**");
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{

        http.cors().configurationSource(request -> {
            CorsConfiguration config = new CorsConfiguration();
            config.addAllowedOrigin("*");
            config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
            config.addAllowedHeader("*");
            return config;
        });

        http
                .csrf().disable() // jwt 토큰 사용을 위한 설정
                .headers().frameOptions().sameOrigin()
                .and()
                .httpBasic().disable()
                .formLogin().disable()
                .addFilterBefore(new JwtFilter(tokenProvider), UsernamePasswordAuthenticationFilter.class)
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and() //예외처리
                .exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint) //customEntryPoint
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .and() // 인증
                .authorizeRequests()
                .anyRequest().permitAll() //일단 전체 허용
                .and()
                ;
        
        return http.build();
    }
}
