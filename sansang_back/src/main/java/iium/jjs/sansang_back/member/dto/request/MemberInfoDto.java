package iium.jjs.sansang_back.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDto {

    @NotBlank
    private String memberPwd;

    @NotBlank
    private String memberName;

    private String zipCode;

    private String address;

    private String addressDetail;

    private String profile;

}
