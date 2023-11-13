package iium.jjs.sansang_back.member.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MemberInfoDto {

    @NotBlank(message = "이름은 필수입니다.")
    private String memberName;

    private String auth;

    private String zipCode;

    private String address;

    private String addressDetail;

    private Object profile;


    public boolean isProfileFile() {
        return profile instanceof MultipartFile;
    }
    public MultipartFile getProfileFile() {
        return isProfileFile() ? (MultipartFile) profile : null;
    }

    public String getProfileString() {
        return !isProfileFile() ? (String) profile : null;
    }

}
