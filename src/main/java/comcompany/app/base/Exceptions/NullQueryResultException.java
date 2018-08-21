package comcompany.app.base.Exceptions;

//when caught different view with information about empty query(can be same view for each query) result should be returned
public class NullQueryResultException extends RuntimeException {
    public NullQueryResultException(String message) {
        super(message);
    }
}
