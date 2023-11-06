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
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {


    private final TokenProvider tokenProvider;

    @Value("${jwt.refreshName}")
    private String refreshName;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String accessToken = tokenProvider.resolveToken(request);

        if(request.getRequestURI().equals("/auth/reissue")){
            String refreshToken = getRefreshToken(request);
            if(!tokenProvider.refreshValidateToken(refreshToken)){
                // 쿠키도 삭제
                tokenProvider.deleteCookie(request.getCookies(), response);
                throw new RefreshTokenException("RefreshToken ERROR");
            }
            Authentication authentication = tokenProvider.getAuthentication(refreshToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        if (accessToken != null) {
            if(!tokenProvider.accessValidateToken(accessToken)) throw new TokenException("AccessToken ERROR");

            if (!request.getRequestURI().equals("/auth/reissue")) {
                Authentication authentication = tokenProvider.getAuthentication(accessToken);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);

    }

    private String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        return Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals("sangRefresh"))
          .findAny()
          .map(Cookie::getValue)
          .orElseThrow(() -> new RefreshTokenException("Cookie 없음"));
    }

}
