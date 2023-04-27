package cho.o.me.blog.exception;

public class KeyGenerationException extends RuntimeException{
    public KeyGenerationException(String message) {
        super(message);
    }

    public KeyGenerationException(String message, Throwable cause) {
        super(message, cause);
    }
}
