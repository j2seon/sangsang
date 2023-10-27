package iium.jjs.sansang_back.member.dto.request;

import iium.jjs.sansang_back.member.dto.response.MemberDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

//로그인용 dto
@Getter
@Setter
@NoArgsConstructor
@ToString
public class LoginDto {

    @NotBlank
    private String id;

    @NotBlank
    private String pwd;



}
