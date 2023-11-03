package iium.jjs.sansang_back.exception;

public class LoginFailException extends RuntimeException{

    public LoginFailException(String message) {
        super(message);
    }
}
