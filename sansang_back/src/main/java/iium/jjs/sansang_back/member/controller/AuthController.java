package iium.jjs.sansang_back.member.controller;

import iium.jjs.sansang_back.common.dto.ResponseDto;
import iium.jjs.sansang_back.member.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;


    //로그인
    @PostMapping("/login")
    public ResponseEntity<ResponseDto> login(){

        return null;
    }


    //토큰 재발생
    @PostMapping("/reissue") 
    public ResponseEntity<ResponseDto> reissue(){
        return null;
    }



}
