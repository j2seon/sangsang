package iium.jjs.sansang_back.member.service;

import iium.jjs.sansang_back.common.service.RedisService;
import iium.jjs.sansang_back.exception.LoginFailException;
import iium.jjs.sansang_back.exception.NotFoundMemberException;
import iium.jjs.sansang_back.jwt.dto.TokenDto;
import iium.jjs.sansang_back.jwt.TokenProvider;
import iium.jjs.sansang_back.member.dto.request.LoginDto;
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
    public TokenDto reissueToken(Cookie cookie){

        String token = cookie.getValue();

        log.info("[AuthService] reissueToken token={}", token);
        String memberId = "";

        if(tokenProvider.validateToken(token)){
             memberId= tokenProvider.getAuthentication(token).getName();
        }

        String refreshToken = redisService.getValues(memberId);
        log.info("[AuthService] reissueToken refreshtoken={}", refreshToken);

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFoundMemberException("해당 아이디가 존재하지 않습니다."));

        String newAccessToken = tokenProvider.createAccessToken(member);
        String newRefreshToken = tokenProvider.createRefreshToken(member);

        redisService.setValues(memberId, newRefreshToken);

        log.info("[AuthService] reissueToken newRefreshToken={}", newRefreshToken);

        return TokenDto.builder()
                .memberId(memberId)
                .auth(member.getAuthority().toString())
                .accessToken(newAccessToken)
                .build();
    }
}
