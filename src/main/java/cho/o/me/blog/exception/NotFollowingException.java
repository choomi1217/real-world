package cho.o.me.blog.exception;

import lombok.Getter;

@Getter
public class NotFollowingException extends MyException{
    public NotFollowingException(ErrorCode code, String message, Throwable cause) {
        super(code, message, cause);
    }

    public NotFollowingException(String message) {
        super(ErrorCode.BadRequest , message, null);
    }
}
