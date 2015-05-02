package voting.model;

public class DuplicatePollException extends Exception {
    private static final long serialVersionUID = -4663400356441299841L;

    public DuplicatePollException(final String message) {
        super(message);
    }
}
