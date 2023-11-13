package iium.jjs.sansang_back.exception.dto;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiExceptionDTO {

  private int status;
  private String message;


}
