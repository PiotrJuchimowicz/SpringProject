package comcompany.app.base.Exceptions;

//thrown when form is incorrect-different view contains information about bad data in form must be returned(can be same for each bad form)
public class IncorrectFormDataException extends RuntimeException {

    public IncorrectFormDataException(String message) {
        super(message);
    }
}
