package iium.jjs.sansang_back.jwt;

import iium.jjs.sansang_back.member.dto.response.MemberDto;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String auth; // 권한
    private String accessToken; // 엑세스 토큰
    private Long accessTokenExpiredTime; // 엑세스 토큰 만료 시간
    private MemberDto memberDto; // 회원 정보



}
