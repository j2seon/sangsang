package iium.jjs.sansang_back.member.dto.request;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.util.annotation.Nullable;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

//회원가입용 dto
@Getter
@Setter
@NoArgsConstructor
@ToString
public class JoinDto {

    @NotBlank(message = "id는 필수입니다")
    private String id;

    @NotBlank(message = "password는 필수입니다")
    private String pwd;

    @NotBlank(message = "name은 필수입니다.")
    private String name;

    private String zipCode;

    private String address;

    private String addressDetail;


    @Builder
    public JoinDto(String id, String pwd, String name, String zipCode, String address, String addressDetail) {
        this.id = id;
        this.pwd = pwd;
        this.name = name;
        this.zipCode = zipCode;
        this.address = address;
        this.addressDetail = addressDetail;
    }
}
