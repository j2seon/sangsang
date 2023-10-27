package iium.jjs.sansang_back.jwt;

import iium.jjs.sansang_back.exception.TokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
@Slf4j
public class TokenProvider {

//    @Value("jwt.access-token-expired") // 왜 안읽히지
    private static long accessTokenExpiredTime = 1000 * 60 * 60;;

//    @Value("jwt.refresh-token-expired")
    private static long refreshTokenExpiredTime = 1000 * 60 * 60 * 24 * 7;
    ;
    private final Key key;

    private static final String BEARER_TYPE = "Bearer";

    public TokenProvider(@Value("${jwt.secret}") String secretKey) {
        byte[] keyBytes = Decoders.BASE64.decode(secretKey);
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    public TokenDto generateToken(Authentication authentication){

        Jwts.builder()
                .setExpiration(
                        new Date(System.currentTimeMillis() + accessTokenExpiredTime)
                )
                .setIssuedAt(new Date())
                //클레임추가

                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
        return null;
    }
    public String getUserId(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key).build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    private Claims parseClaims(String token) {
        try {
            return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
        } catch (ExpiredJwtException e) {
            return e.getClaims();
        }
    }

    public boolean validateToken(String token) {

        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        }catch(io.jsonwebtoken.security.SecurityException | MalformedJwtException e){
            log.info("[TokenProvider] 잘못된 JWT 서명입니다.");
            throw new TokenException("잘못된 JWT 서명입니다.");
        }catch(ExpiredJwtException e){
            log.info("[TokenProvider] 만료된 JWT 토큰입니다.");
            throw new TokenException("만료된 JWT 토큰입니다.");
        }catch(IllegalArgumentException e){
            log.info("[TokenProvider] JWT 토큰이 잘못되었습니다.");
            throw new TokenException("JWT 토큰이 잘못되었습니다.");
        }
    }

}
