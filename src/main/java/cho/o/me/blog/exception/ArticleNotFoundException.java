package cho.o.me.blog.exception;

import lombok.Getter;

@Getter
public class ArticleNotFoundException extends MyException {

    public ArticleNotFoundException(String message, Throwable cause) {
        super(ErrorCode.NotFound, message, cause);
    }
    public ArticleNotFoundException(String message) {
        super(ErrorCode.NotFound, message, null);
    }
}
