package iium.jjs.sansang_back.member.service;

import iium.jjs.sansang_back.exception.NotFountMemberException;
import iium.jjs.sansang_back.member.dto.request.JoinDto;
import iium.jjs.sansang_back.member.dto.request.LoginDto;
import iium.jjs.sansang_back.member.dto.response.MemberDto;
import iium.jjs.sansang_back.member.entity.Address;
import iium.jjs.sansang_back.member.entity.Authority;
import iium.jjs.sansang_back.member.entity.Member;
import iium.jjs.sansang_back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;


    // 회원가입
    @Transactional
    public void register(JoinDto joinDto) { // 다른 타입 반환해주자

        log.info("joinDto {}" , joinDto);



        Member newMember = Member.builder()
                                    .memberId(joinDto.getMemberId())
                                    .memberName(joinDto.getMemberName())
                                    .memberPwd(passwordEncoder.encode(joinDto.getMemberPwd()))
                                    .profile(joinDto.getProfile())
                                    .address(new Address(joinDto.getZipCode(), joinDto.getAddress(), joinDto.getAddressDetail()))
                                    .profile(joinDto.getProfile())
                                    .createdAt(LocalDateTime.now())
                                    .authority(Authority.ROLE_USER)
                                    .build();

        memberRepository.save(newMember);
        
    }

    // 전체 조회 -> 페이징 처리하기
    public List<MemberDto> getAllMember() {

        List<Member> findAllMember = memberRepository.findAll();

        return findAllMember.stream().map(MemberDto::new).collect(Collectors.toList());
    }

    // 한명 조회
    public MemberDto findById(String memberId){
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFountMemberException("해당 회원이 존재하지 않습니다"));

        return new MemberDto(member);
    }



}
