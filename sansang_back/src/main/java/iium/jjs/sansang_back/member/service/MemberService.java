package iium.jjs.sansang_back.member.service;

import iium.jjs.sansang_back.util.FileUploadUtils;
import iium.jjs.sansang_back.exception.FileUploadException;
import iium.jjs.sansang_back.exception.NotFoundMemberException;
import iium.jjs.sansang_back.member.dto.request.JoinDto;
import iium.jjs.sansang_back.member.dto.request.MemberInfoDto;
import iium.jjs.sansang_back.member.dto.request.SearchMemberDto;
import iium.jjs.sansang_back.member.dto.response.MemberDto;
import iium.jjs.sansang_back.member.entity.Address;
import iium.jjs.sansang_back.member.entity.Authority;
import iium.jjs.sansang_back.member.entity.Member;
import iium.jjs.sansang_back.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;

    private final PasswordEncoder passwordEncoder;

    private final FileUploadUtils fileUploadUtils;

    @Value("${files.file-dir}")
    private String FILE_DIR;

    @Value("${files.file-url}")
    private String FILES_URL;

    // 회원가입
    @Transactional
    public void register(JoinDto joinDto) { // 다른 타입 반환해주자

        log.info("joinDto {}" , joinDto);

        Member newMember = Member.builder()
                                    .memberId(joinDto.getId())
                                    .memberName(joinDto.getName())
                                    .memberPwd(passwordEncoder.encode(joinDto.getPwd()))
                                    .address(new Address(joinDto.getZipCode(), joinDto.getAddress(), joinDto.getAddressDetail()))
                                    .createdAt(LocalDateTime.now())
                                    .authority(Authority.ROLE_USER)
                                    .build();

        fileLoad(joinDto.getProfile(), newMember);

        memberRepository.save(newMember);
        
    }

    // 전체 조회 -> 페이징 처리하기
    public Page<MemberDto> getAllMember(SearchMemberDto search, Pageable pageable) {

        return memberRepository.conditionList(search, pageable);
    }

    // 한명 조회
    public MemberDto findById(String memberId){
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFoundMemberException("해당 회원이 존재하지 않습니다"));

        return new MemberDto(member);
    }

    // 회원정보수정
    @Transactional
    public MemberDto updateMemberInfo(String memberId, MemberInfoDto memberInfoDto){
        Member member = memberRepository.findByMemberId(memberId).orElseThrow(() -> new NotFoundMemberException("해당 회원이 존재하지 않습니다"));

        // 이거만 따로 정리하기
        member.setMemberPwd(passwordEncoder.encode(memberInfoDto.getMemberPwd()));
        member.setAddress(new Address(memberInfoDto.getZipCode(), memberInfoDto.getAddress(), memberInfoDto.getAddressDetail()));
        member.setProfile(memberInfoDto.getProfile());
        member.setMemberName(memberInfoDto.getMemberName());

        return new MemberDto(member);
    }

    // 프로필 사진 업로드
    private void fileLoad(MultipartFile multipartFile, Member member){

        log.info("[MemberService] MultipartFile= {}", multipartFile );

        if(multipartFile == null) return;

        try {
            String saveFile = FileUploadUtils.saveFile(FILE_DIR, multipartFile);
            member.setProfile(saveFile);

        } catch (IOException e) {
            FileUploadUtils.deleteFile(FILE_DIR, multipartFile.getName());
            throw new FileUploadException("파일 저장 중 오류가 발생했습니다");
        }
    }


}
