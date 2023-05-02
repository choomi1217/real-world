package cho.o.me.blog.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {

    BadRequest(HttpStatus.BAD_REQUEST, "Bad Request"),
    NotFound(HttpStatus.NOT_FOUND, "resource can't be found to fulfill the request"),
    Forbidden(HttpStatus.FORBIDDEN, "request may be valid but the user doesn't have permissions to perform the action"),
    Unauthorized(HttpStatus.UNAUTHORIZED, "request requires authentication but it isn't provided")
    ;
    private HttpStatus httpStatus;
    private String message;

    ErrorCode(HttpStatus httpStatus, String message) {
        this.httpStatus = httpStatus;
        this.message = message;
    }

    public String getDefaultMessage() {
        return this.message;
    }
}
