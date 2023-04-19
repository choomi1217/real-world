package cho.o.me.blog.exception;

import lombok.Getter;

@Getter
public class UserNotFoundElementException extends MyException {

    public UserNotFoundElementException(String message, Throwable cause) {
        super(ErrorCode.NotFound, message, cause);
    }
    public UserNotFoundElementException(String message) {
        super(ErrorCode.NotFound, message, null);
    }
}
