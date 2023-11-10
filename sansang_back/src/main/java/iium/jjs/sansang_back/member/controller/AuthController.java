package iium.jjs.sansang_back.member.controller;

import iium.jjs.sansang_back.common.dto.ResponseDto;
import iium.jjs.sansang_back.jwt.dto.TokenDto;
import iium.jjs.sansang_back.member.dto.MemberDetailImpl;
import iium.jjs.sansang_back.member.dto.request.LoginDto;
import iium.jjs.sansang_back.member.dto.response.LoginResponseDto;
import iium.jjs.sansang_back.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;
import java.util.Queue;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(@Valid @RequestBody LoginDto loginDto, HttpServletResponse response){

        LoginResponseDto login = authService.login(loginDto, response);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .data(login)
                        .message(login.getTokenDto().getMemberId() + "님 로그인 완료")
                        .build());
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<ResponseDto> logout(@AuthenticationPrincipal MemberDetailImpl memberDetail, @CookieValue(value = "sangRefresh", required = false) Cookie cookie){

        authService.logout(memberDetail.getUsername());

        log.info("Cookie={}", cookie.getValue());
        log.info("Cookie={}", cookie.getMaxAge());
        log.info("Cookie={}", cookie.getName());

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .message("로그아웃")
                        .build());
    }


    //토큰 재발행
    @PostMapping("/reissue")
    public ResponseEntity<ResponseDto> reissue(@CookieValue(value = "sangRefresh", required = false) Cookie cookie, HttpServletResponse response) {

        TokenDto tokenDto = authService.reissueToken(cookie, response);

        return ResponseEntity.ok()
                .body(ResponseDto.builder()
                        .status(HttpStatus.OK)
                        .data(tokenDto)
                        .message("accessToken 재발급 성공")
                        .build());
    }



}
