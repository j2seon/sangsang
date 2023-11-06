package iium.jjs.sansang_back.member.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//회원가입용 dto
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JoinDto {

    @NotBlank
    private String id;

    @NotBlank
    private String pwd;

    @NotBlank
    private String name;

    private String zipCode;

    private String address;

    private String addressDetail;

    private MultipartFile profile;

    @Builder
    public JoinDto(String id, String pwd, String name, String zipCode, String address, String addressDetail, MultipartFile profile) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
        this.profile = profile;
    }
}
