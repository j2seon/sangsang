package iium.jjs.sansang_back.jwt.dto;

import iium.jjs.sansang_back.member.dto.response.MemberDto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TokenDto {

    private String tokenType;
    private String auth; // 권한
    private String memberId; // 회원 아이디
    private String accessToken; // 엑세스 토큰
    private Date accessTokenExpiredTime; // 엑세스 토큰 만료 시간

}
