package iium.jjs.sansang_back.member.controller;

import iium.jjs.sansang_back.common.dto.ResponseDto;
import iium.jjs.sansang_back.jwt.dto.TokenDto;
import iium.jjs.sansang_back.member.dto.MemberDetailImpl;
import iium.jjs.sansang_back.member.dto.request.LoginDto;
import iium.jjs.sansang_back.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto loginDto){

        TokenDto tokenDto = authService.login(loginDto);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .data(tokenDto)
                        .message(tokenDto.getMemberId() + "님 로그인 성공")
                        .build());
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@AuthenticationPrincipal MemberDetailImpl memberDetail){

        authService.logout(memberDetail.getUsername());

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("로그아웃")
                        .build());
    }


    //토큰 재발행
    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@AuthenticationPrincipal MemberDetailImpl memberDetail) {
        TokenDto tokenDto = authService.reissueToken(memberDetail.getUsername());

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .data(tokenDto)
                        .message("accessToken 재발급 성공")
                        .build());
    }



}
