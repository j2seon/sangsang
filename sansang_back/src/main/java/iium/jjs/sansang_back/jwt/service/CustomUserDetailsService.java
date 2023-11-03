package iium.jjs.sansang_back.jwt.service;

import iium.jjs.sansang_back.exception.NotFoundMemberException;
import iium.jjs.sansang_back.member.dto.MemberDetailImpl;
import iium.jjs.sansang_back.member.entity.Member;
import iium.jjs.sansang_back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFoundMemberException("등록된 아이디가 없습니다."));

        MemberDetailImpl memberDetails = new MemberDetailImpl(member);

        return memberDetails;
    }
}
