package iium.jjs.sansang_back.member.service;

import iium.jjs.sansang_back.exception.LoginFailedException;
import iium.jjs.sansang_back.exception.NotFountMemberException;
import iium.jjs.sansang_back.jwt.TokenDto;
import iium.jjs.sansang_back.jwt.TokenProvider;
import iium.jjs.sansang_back.member.dto.request.LoginDto;
import iium.jjs.sansang_back.member.entity.Member;
import iium.jjs.sansang_back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final TokenProvider tokenProvider;


    public TokenDto login(LoginDto loginDto){

        Member member = memberRepository.findByMemberId(loginDto.getId()).orElseThrow(() -> new NotFountMemberException("해당 아이디가 존재하지 않습니다."));

       if(passwordEncoder.matches(loginDto.getPwd(), member.getMemberPwd())){
           throw new LoginFailedException("잘못된 비밀번호 입니다.");
        }

        String accessToken = tokenProvider.createAccessToken(member);
        String refreshToken = tokenProvider.createRefreshToken();


        return null;
    }

//    public TokenDto reissue(LoginDto loginDto){
//
//    }

}
