package iium.jjs.sansang_back.exception.dto;

public class RefreshTokenException extends RuntimeException{
  public RefreshTokenException(String message) {
    super(message);
  }
}
