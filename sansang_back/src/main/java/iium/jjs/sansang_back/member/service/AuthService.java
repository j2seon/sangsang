package iium.jjs.sansang_back.member.service;

import iium.jjs.sansang_back.common.service.RedisService;
import iium.jjs.sansang_back.exception.LoginFailException;
import iium.jjs.sansang_back.exception.NotFoundMemberException;
import iium.jjs.sansang_back.exception.dto.RefreshTokenException;
import iium.jjs.sansang_back.jwt.dto.TokenDto;
import iium.jjs.sansang_back.jwt.TokenProvider;
import iium.jjs.sansang_back.member.dto.request.LoginDto;
import iium.jjs.sansang_back.member.dto.response.LoginResponseDto;
import iium.jjs.sansang_back.member.entity.Member;
import iium.jjs.sansang_back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseCookie;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final RedisService redisService;

    public TokenDto login(LoginDto loginDto, HttpServletResponse response){

        Member member = memberRepository.findByMemberId(loginDto.getId()).orElseThrow(() -> new NotFoundMemberException("해당 아이디가 존재하지 않습니다."));
       if(!passwordEncoder.matches(loginDto.getPwd(), member.getMemberPwd())){
           throw new LoginFailException("잘못된 비밀번호 입니다.");
        }

        // 액세스토큰 리프레시토큰 발행
        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken(member);

        log.info("[AuthService]accessToken = {} refreshToken={}",accessToken,refreshToken);

        // 리프레시토큰 redis에 저장 / secure 쿠키로 전달
        redisService.setValues(member.getMemberId(), refreshToken);
        ResponseCookie cookie = tokenProvider.generateRefreshTokenInCookie(refreshToken);
        response.setHeader("Set-Cookie", cookie.toString());



          return TokenDto.builder()
                .auth(member.getAuthority().toString())
                .memberId(member.getMemberId())
                .accessToken(accessToken)
                .accessTokenExpiredTime(tokenProvider.getExpiredDate(accessToken))
                .build();
    }

    // 로그아웃
    public void logout(String memberId){
        //리프레시 토큰 삭제
        redisService.deleteValues(memberId);
    }


    //재발급
    public TokenDto reissueToken(Cookie cookie, HttpServletResponse response){

        String cookieValue = cookie.getValue();

        log.info("[AuthService] cookieValue token={}", cookieValue);
        String memberId = tokenProvider.getAuthentication(cookieValue).getName();

        //1. redis에서 아이디로 refreshToken가져오고
        String refreshToken = redisService.getValues(memberId);
        log.info("[AuthService] refreshToken token={}", refreshToken);

        //2. 만약 cookie에 담긴 값과 redis에 담긴 값이 같지 않으면 에러 및 refresh 쿠키 삭제 -> 앞단에서 처리하도록 수정해도되겠다
        if(!refreshToken.equals(cookieValue)) {
          tokenProvider.deleteCookie(cookie, response);
          throw new RefreshTokenException("일치하지 않는 토큰입니다");
        }

        //3. 같다면 member엔티티를 가져와서 
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFoundMemberException("해당 아이디가 존재하지 않습니다."));

        //4. 새로 access, refresh를 만들고
        String newAccessToken = tokenProvider.createAccessToken(member);
        String newRefreshToken = tokenProvider.createRefreshToken(member);

        //5. 바뀐 refresh 도 저장시켜주고 다시쿠키저장
        redisService.setValues(memberId, newRefreshToken);
        ResponseCookie newCookie = tokenProvider.generateRefreshTokenInCookie(refreshToken);
        response.setHeader("Set-Cookie", cookie.toString());
        log.info("[AuthService] reissueToken newRefreshToken={}", newRefreshToken);

        //6. access를 클라이언트에게 반환
        return TokenDto.builder()
                .memberId(memberId)
                .auth(member.getAuthority().toString())
                .accessToken(newAccessToken)
                .build();
    }
}
