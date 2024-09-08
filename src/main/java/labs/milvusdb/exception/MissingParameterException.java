package labs.milvusdb.exception;

/**
 * Application exception
 */
public class MissingParameterException extends IllegalArgumentException {
    public MissingParameterException(String message) {
        super(message);
    }
}
