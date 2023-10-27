package iium.jjs.sansang_back.member.dto.request;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//회원가입용 dto
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JoinDto {


    @NotBlank
    private String memberId;

    @NotBlank
    private String memberPwd;

    @NotBlank
    private String memberName;

    private String zipCode;

    private String address;

    private String addressDetail;

    private String profile;

    @Builder
    public JoinDto(String memberId, String memberPwd, String memberName, String zipCode, String address, String addressDetail, String profile) {
        this.memberId = memberId;
        this.memberPwd = memberPwd;
        this.memberName = memberName;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.profile = profile;
    }
}
