package iium.jjs.sansang_back.exception;

import iium.jjs.sansang_back.exception.dto.ApiExceptionDTO;
import iium.jjs.sansang_back.exception.dto.ErrorCode;
import iium.jjs.sansang_back.exception.dto.ErrorResponse;
import iium.jjs.sansang_back.exception.dto.RefreshTokenException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice
public class ExceptionAdvice {


  @ExceptionHandler(TokenException.class)
  public ResponseEntity<ApiExceptionDTO> exceptionHandler(TokenException e) {
    return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
      .body(new ApiExceptionDTO(HttpStatus.UNAUTHORIZED.value(), e.getMessage()));
  }

  @ExceptionHandler(RefreshTokenException.class)
  public ResponseEntity<ApiExceptionDTO> exceptionHandler(RefreshTokenException e) {
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

  // 나중에 밑의 형태로 exception 정리
  @ExceptionHandler(MethodArgumentNotValidException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
    final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
    return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
  }

  @ExceptionHandler(BindException.class)
  @ResponseStatus(HttpStatus.BAD_REQUEST)
  protected ErrorResponse handleBindException(org.springframework.validation.BindException e) {
    final List<ErrorResponse.FieldError> fieldErrors = getFieldErrors(e.getBindingResult());
    return buildFieldErrors(ErrorCode.INPUT_VALUE_INVALID, fieldErrors);
  }


  private ErrorResponse buildFieldErrors(ErrorCode errorCode, List<ErrorResponse.FieldError> errors) {
    return ErrorResponse.builder()
      .status(errorCode.getStatus())
      .message(errorCode.getMessage())
      .errors(errors)
      .build();
  }

  private List<ErrorResponse.FieldError> getFieldErrors(BindingResult bindingResult){
    List<FieldError> fieldErrors = bindingResult.getFieldErrors();

    return fieldErrors.parallelStream()
        .map(err -> ErrorResponse.FieldError
                    .builder()
                    .reason(err.getDefaultMessage())
                    .field(err.getField())
                    .value((String) err.getRejectedValue())
                    .build())
        .collect(Collectors.toList());
  }


}
