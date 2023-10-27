package iium.jjs.sansang_back.member.service;

import iium.jjs.sansang_back.exception.NotFountMemberException;
import iium.jjs.sansang_back.member.entity.Member;
import iium.jjs.sansang_back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberServiceImpl implements MemberService, UserDetailsService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String memberId) throws UsernameNotFoundException {
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFountMemberException("등록된 아이디가 없습니다."));




        return null;
    }
}
