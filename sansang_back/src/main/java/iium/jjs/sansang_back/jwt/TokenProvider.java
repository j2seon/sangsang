package iium.jjs.sansang_back.jwt;

import iium.jjs.sansang_back.exception.TokenException;
import iium.jjs.sansang_back.exception.dto.RefreshTokenException;
import iium.jjs.sansang_back.member.dto.MemberDetailImpl;
import iium.jjs.sansang_back.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.server.Cookie.SameSite;

import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Arrays;
import java.util.Date;
import java.util.Optional;


@Slf4j
@Component
public class TokenProvider {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth"; // 권한이름
    private static final String BEARER_TYPE = "Bearer "; // 토큰 타입
    private static final String MEMBER_ID = "memberId"; //

    private final long accessTokenExpiredTime; // 액세스 토큰 시간

    private final long refreshTokenExpiredTime; // 리프레시 토큰 시간
    private final Key key; // 키

    @Value("${jwt.refreshName}")
    private String refreshTokenName;
    private final UserDetailsService userDetailsService;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.refresh-token-expired}") long refreshTokenExpiredTime,
                         @Value("${jwt.access-token-expired}") long accessTokenExpiredTime,
                         UserDetailsService userDetailsService
    ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiredTime = accessTokenExpiredTime * 1000;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime * 1000;
        this.userDetailsService = userDetailsService;
    }

    // 엑세스 토큰 생성
    public String createAccessToken(Member member) {
        log.info("[TokenProvider] createAccessToken =======");
        log.info("[TokenProvider] accessTokenExpiredTime ={}=======", accessTokenExpiredTime);
        return Jwts.builder()
                .setExpiration(
                        new Date(System.currentTimeMillis() + accessTokenExpiredTime)
                )
                .setSubject("access-token")
                .setIssuedAt(new Date())
                .claim(AUTHORITIES_KEY, member.getAuthority())
                .claim(MEMBER_ID, member.getMemberId())
                .claim("profile", member.getProfile())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(Member member) {
        log.info("[TokenProvider] createRefreshToken =======");

        //redis에 저장하는 로직

        return Jwts.builder()
                .setExpiration(
                        new Date(System.currentTimeMillis() + refreshTokenExpiredTime)
                )
                .setSubject("refresh-token")
                .setIssuedAt(new Date())
                .claim(MEMBER_ID, member.getMemberId())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 인증 객체
    public Authentication getAuthentication(String token) {

        Claims claims = parseClaims(token);
        String memberId = claims.get(MEMBER_ID).toString();

        MemberDetailImpl userDetails = (MemberDetailImpl) userDetailsService.loadUserByUsername(memberId);

        if(!userDetails.isEnabled()){
            throw new DisabledException("사용할 수 없는 계정입니다.");
        }

        log.info("[TokenProvider] userDetails: ${}/===== ", userDetails);

        return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
    }


    // AccessToken에서 클레임 추출
    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    // 토큰 확인
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);

        log.info("===== bearerToken 토큰확인  = {}======", bearerToken);

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith(BEARER_TYPE)) {
            return bearerToken.substring(7);
        }
        return null;
    }

    public Date getExpiredDate(String token){
        return parseClaims(token).getExpiration();
    }

    // http secure 쿠키 생성
    public ResponseCookie generateRefreshTokenInCookie(String token) {

        return ResponseCookie.from(refreshTokenName, token)
                .maxAge(refreshTokenExpiredTime / 1000)
                .path("/")
                .secure(true)
                .sameSite(SameSite.NONE.name())
                .httpOnly(true)
                .build();
    }

    // 토큰의 등록된 해당 회원의 아이디 추출
    public String getEmpNo(String token) {
        return Jwts.parserBuilder()
          .setSigningKey(key).build()
          .parseClaimsJws(token)
          .getBody()
          .get(MEMBER_ID).toString();
    }

    public void deleteCookie(Cookie[] cookies, HttpServletResponse response) {
        Optional<Cookie> sangRefreshCookie = Arrays.stream(cookies)
          .filter(cookie -> "sangRefresh".equals(cookie.getName()))
          .findAny();

        if (sangRefreshCookie.isPresent()) {
            Cookie cookieToRemove = sangRefreshCookie.get();
            cookieToRemove.setMaxAge(0);
            cookieToRemove.setPath("/");
            response.addCookie(cookieToRemove);
        }
    }

    String getRefreshToken(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();

        if (cookies == null) {
            throw new RefreshTokenException("Cookie 없음");
        }

        log.info("cookies={}", Arrays.toString(cookies));

        return Arrays.stream(cookies)
          .filter(cookie -> cookie.getName().equals("sangRefresh"))
          .findAny()
          .map(Cookie::getValue)
          .orElseThrow(() -> new RefreshTokenException("Cookie 없음"));
    }


    public void deleteCookie(Cookie cookies, HttpServletResponse response) {
            cookies.setMaxAge(0);
            cookies.setPath("/");
            response.addCookie(cookies);
    }

    // 토큰 유효성 검사
    public boolean accessValidateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다");
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("AccessToken ERROR");
        } catch (UnsupportedJwtException e) {
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new TokenException("AccessToken ERROR");
        } catch (IllegalArgumentException e) {
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("AccessToken ERROR");
        }
    }

    public boolean refreshValidateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new RefreshTokenException("RefreshToken ERROR");
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new RefreshTokenException("RefreshToken ERROR");
        } catch (UnsupportedJwtException e) {
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new RefreshTokenException("RefreshToken ERROR");
        } catch (IllegalArgumentException e) {
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new RefreshTokenException("RefreshToken ERROR");
        }
    }


}
