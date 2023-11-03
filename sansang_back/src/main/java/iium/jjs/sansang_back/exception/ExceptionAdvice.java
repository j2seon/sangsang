package iium.jjs.sansang_back.exception;

import iium.jjs.sansang_back.exception.dto.ApiExceptionDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {
  @ExceptionHandler(TokenException.class)
  public ResponseEntity<ApiExceptionDTO> exceptionHandler(TokenException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
      .body(new ApiExceptionDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
  }
  @ExceptionHandler(LoginFailException.class)
  public ResponseEntity<ApiExceptionDTO> exceptionHandler(LoginFailException e){
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
      .body(new ApiExceptionDTO(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
  }

  @ExceptionHandler(NotFoundMemberException.class)
  public ResponseEntity<ApiExceptionDTO> exceptionHandler(NotFoundMemberException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(new ApiExceptionDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }

  @ExceptionHandler(FileUploadException.class)
  public ResponseEntity<ApiExceptionDTO> exceptionHandler(FileUploadException e){
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
      .body(new ApiExceptionDTO(HttpStatus.NOT_FOUND.value(), e.getMessage()));
  }



}
