package iium.jjs.sansang_back.jwt;

import iium.jjs.sansang_back.common.service.RedisService;
import iium.jjs.sansang_back.exception.TokenException;
import iium.jjs.sansang_back.exception.dto.RefreshTokenException;
import io.jsonwebtoken.IncorrectClaimException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final TokenProvider tokenProvider;

    @Value("${jwt.refreshName}")
    private String refreshName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = tokenProvider.resolveToken(request);
        log.info("[jwtFilter] accessToken = {}",accessToken);

        if(request.getRequestURI().equals("/auth/reissue")){

            String refreshToken = tokenProvider.getRefreshToken(request);
            log.info("[jwtFilter] refreshToken = {}",refreshToken);

            if(!tokenProvider.refreshValidateToken(refreshToken)){
                Authentication authentication = tokenProvider.getAuthentication(refreshToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }


        if (accessToken != null && tokenProvider.accessValidateToken(accessToken)) {
            log.info("[jwtFilter] access start");

            if (!request.getRequestURI().equals("/auth/reissue")) {
                log.info("[jwtFilter] access auth 지정");
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        log.info("[jwtFilter] end");

        filterChain.doFilter(request, response);

    }



}
