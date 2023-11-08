package iium.jjs.sansang_back.member.dto.response;

import iium.jjs.sansang_back.jwt.dto.TokenDto;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
@Builder
public class LoginResponseDto {

  private String profile;
  private TokenDto tokenDto;
}
