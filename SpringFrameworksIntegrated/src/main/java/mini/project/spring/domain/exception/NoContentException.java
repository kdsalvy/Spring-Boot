package mini.project.spring.domain.exception;

public class NoContentException extends RuntimeException {

    private static final long serialVersionUID = 5622516645486430670L;

    public NoContentException(String msg) {
        super(msg);
    }

}
