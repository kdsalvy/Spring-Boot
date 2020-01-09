package mini.project.spring.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidOperationException extends RuntimeException {

    private static final long serialVersionUID = 3346304617329092750L;

    public InvalidOperationException(String msg) {
        super(msg);
    }
}
