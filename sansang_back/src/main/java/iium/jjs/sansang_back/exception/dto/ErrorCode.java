package iium.jjs.sansang_back.exception.dto;

import lombok.Getter;

@Getter
public enum ErrorCode {

  INPUT_VALUE_INVALID("input01","입력이 올바르지 않습니다", 400);
  private final String code;
  private final String message;
  private final int status;

  ErrorCode(String code, String message, int status){
    this.code = code;
    this.message = message;
    this.status = status;
  }
}
