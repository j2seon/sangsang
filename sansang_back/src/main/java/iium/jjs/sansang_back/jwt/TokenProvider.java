package iium.jjs.sansang_back.jwt;

import iium.jjs.sansang_back.exception.TokenException;
import iium.jjs.sansang_back.jwt.dto.TokenDto;
import iium.jjs.sansang_back.member.dto.MemberDetailImpl;
import iium.jjs.sansang_back.member.entity.Member;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Key;
import java.util.Date;


@Slf4j
@Component
public class TokenProvider {
    public static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String AUTHORITIES_KEY = "auth"; // 권한이름
    private static final String BEARER_TYPE = "Bearer "; // 토큰 타입
    private static final String MEMBER_ID = "memberId"; // 토큰 타입
    private final long accessTokenExpiredTime; // 액세스 토큰 시간
    private final long refreshTokenExpiredTime ; // 리프레시 토큰 시간
    private final Key key; // 키
    private final UserDetailsService userDetailsService;

    public TokenProvider(@Value("${jwt.secret}") String secretKey,
                         @Value("${jwt.access-token-expired}") long accessTokenExpiredTime,
                         @Value("${jwt.refresh-token-expired}") long refreshTokenExpiredTime,
                         UserDetailsService userDetailsService
                         ) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
        this.accessTokenExpiredTime = accessTokenExpiredTime * 1000;
        this.refreshTokenExpiredTime = refreshTokenExpiredTime * 1000;
        this.userDetailsService = userDetailsService;
    }

    // 엑세스 토큰 생성
    public String createAccessToken(Member member){
        log.info("[TokenProvider] createAccessToken =======");
        log.info("[TokenProvider] accessTokenExpiredTime ={}=======",accessTokenExpiredTime);
        return Jwts.builder()
                .setExpiration(
                        new Date(System.currentTimeMillis() + accessTokenExpiredTime)
                )
                .setSubject("access-token")
                .setIssuedAt(new Date())
                .claim(AUTHORITIES_KEY, member.getAuthority())
                .claim(MEMBER_ID, member.getMemberId())
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    // 리프레쉬 토큰 생성
    public String createRefreshToken(){
        log.info("[TokenProvider] createRefreshToken =======");

        String refreshToken  = Jwts.builder()
                                .setExpiration(
                                    new Date(System.currentTimeMillis() + refreshTokenExpiredTime)
                                )
                                .setSubject("refresh-token")
                                .setIssuedAt(new Date())
                                .signWith(key, SignatureAlgorithm.HS512)
                                .compact();
        //redis에 저장하는 로직
        
        return refreshToken;
    }

    // 인증 객체
    public Authentication getAuthentication(String token){

        Claims claims = parseClaims(token);
        String memberId = claims.get(MEMBER_ID).toString();

        MemberDetailImpl userDetails = (MemberDetailImpl) userDetailsService.loadUserByUsername(memberId);
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


        // 토큰 유효성 검사
    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (ExpiredJwtException e) {
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다.");
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("잘못된 JWT 서명입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("[TokenProvider] 지원되지 않는 JWT 토큰입니다.");
            throw new TokenException("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        }
    }



}
