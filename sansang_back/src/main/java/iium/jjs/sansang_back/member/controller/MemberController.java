package iium.jjs.sansang_back.member.controller;

import iium.jjs.sansang_back.common.dto.PageDto;
import iium.jjs.sansang_back.common.dto.PagingResponseDTO;
import iium.jjs.sansang_back.common.dto.ResponseDto;
import iium.jjs.sansang_back.member.dto.request.JoinDto;
import iium.jjs.sansang_back.member.dto.request.MemberInfoDto;
import iium.jjs.sansang_back.member.dto.request.SearchMemberDto;
import iium.jjs.sansang_back.member.dto.response.MemberDto;
import iium.jjs.sansang_back.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


import javax.validation.Valid;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;

    // 가입
    @PostMapping("/join")
    public ResponseEntity<ResponseDto> register(@Valid @ModelAttribute JoinDto joinDto){
    log.info("joindto={}",joinDto);
        memberService.register(joinDto);
        log.info("joinDTO={}",joinDto);

        return ResponseEntity.ok()
                    .body(ResponseDto.builder()
                    .status(HttpStatus.OK)
                    .message("회원가입에 성공했습니다")
                    .build());
    }

    // 전체 조회
    @GetMapping("/members")
    public ResponseEntity<ResponseDto> memberAllList(
            @ModelAttribute SearchMemberDto searchMemberDto,
            @PageableDefault(size = 10) Pageable pageable){

        Page<MemberDto> allMember = memberService.getAllMember(searchMemberDto, pageable);

        PageDto pageInfo = PageDto.builder()
          .page(allMember.getNumber())
          .total(allMember.getTotalElements())
          .realEnd(allMember.getTotalPages())
          .size(pageable.getPageSize())
          .build();

        PagingResponseDTO data = PagingResponseDTO.builder()
          .pageInfo(pageInfo)
          .data(allMember.getContent())
          .build();

        return ResponseEntity.ok()
                        .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("전체 직원 목록 조회 성공")
                        .data(data)
                        .build());
    }

    // 한명 조회
    @GetMapping("/{memberId}")
    public ResponseEntity<ResponseDto> findByMember(@PathVariable String memberId){

        MemberDto findMember = memberService.findById(memberId);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("조회 성공")
                        .data(findMember)
                        .build());
    }

    // 회원 정보 수정
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{memberId}")
    public ResponseEntity<ResponseDto> updateMemberInfo(@PathVariable String memberId, @Valid @RequestBody MemberInfoDto memberInfoDto){

        MemberDto memberDto = memberService.updateMemberInfo(memberId, memberInfoDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message(memberId + "의 회원정보 수정 완료")
                        .data(memberDto)
                        .build());
    }












    }
