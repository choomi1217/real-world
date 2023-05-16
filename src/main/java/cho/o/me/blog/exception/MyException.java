package cho.o.me.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class MyException extends RuntimeException{

    private final ErrorCode code;

    public MyException(ErrorCode code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }
}
