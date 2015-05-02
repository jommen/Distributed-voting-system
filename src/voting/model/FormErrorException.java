package voting.model;

public class FormErrorException extends Exception {

    public FormErrorException() {
        super();
    }

    public FormErrorException(final String message) {
        super(message);
    }

    public FormErrorException(final Throwable throwable) {
        super(throwable);
    }

    public FormErrorException(final String message, final Throwable throwable) {
        super(message, throwable);
    }

}
