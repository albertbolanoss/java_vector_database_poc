package labs.milvusdb.exception;

public class ControllerError {
    private String message;

    private Throwable cause;

    public ControllerError(String message) {
        this.message = message;
    }

    public ControllerError(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
    }

    public String getMessage() {
        return message;
    }

    public Throwable getCause() {
        return cause;
    }
}
