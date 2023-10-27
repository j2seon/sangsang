package iium.jjs.sansang_back.common.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ResponseDto {

    private int status;

    private String message;

    private Object data;

    @Builder
    public ResponseDto(HttpStatus status, String message, Object data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}
