package iium.jjs.sansang_back.member.service;

import iium.jjs.sansang_back.common.service.RedisService;
import iium.jjs.sansang_back.exception.LoginFailedException;
import iium.jjs.sansang_back.exception.NotFountMemberException;
import iium.jjs.sansang_back.jwt.dto.TokenDto;
import iium.jjs.sansang_back.jwt.TokenProvider;
import iium.jjs.sansang_back.member.dto.request.LoginDto;
import iium.jjs.sansang_back.member.entity.Member;
import iium.jjs.sansang_back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;

    private final RedisService redisService;

    public TokenDto login(LoginDto loginDto){

        Member member = memberRepository.findByMemberId(loginDto.getId()).orElseThrow(() -> new NotFountMemberException("해당 아이디가 존재하지 않습니다."));

       if(!passwordEncoder.matches(loginDto.getPwd(), member.getMemberPwd())){
           throw new LoginFailedException("잘못된 비밀번호 입니다.");
        }

        // 액세스토큰 리프레시토큰 발행
        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken();

        log.info("[AuthService]accessToken = {}",accessToken);

        // 리프레시토큰은 redis 저장
        redisService.setValues(member.getMemberId(), refreshToken);

        return TokenDto.builder()
                .auth(member.getAuthority().toString())
                .memberId(member.getMemberId())
                .accessToken(accessToken)
//                .accessTokenExpiredTime(tokenProvider)
                .build();
    }

    // 로그아웃
    public void logout(String memberId){
        //리프레시 토큰 삭제
        redisService.deleteValues(memberId);
    }

    public TokenDto reissueToken(String memberId){

        String refreshToken = redisService.getValues(memberId);
        log.info("[AuthService] reissueToken refreshtoken={}", refreshToken);
//        log.info("[AuthService] token token={}", token);

        if(!tokenProvider.validateToken(refreshToken)){

        }

        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFountMemberException("해당 아이디가 존재하지 않습니다."));

        String newAccessToken = tokenProvider.createAccessToken(member);
        String newRefreshToken = tokenProvider.createRefreshToken();

        redisService.setValues(memberId,newRefreshToken);

        return TokenDto.builder()
                .memberId(memberId)
                .auth(member.getAuthority().toString())
                .accessToken(newAccessToken)
                .build();

    }


}
