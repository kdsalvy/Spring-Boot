package mini.project.spring.domain.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class NoSuchDataFoundToUpdate extends RuntimeException {

    private static final long serialVersionUID = 7745778820260456989L;

    public NoSuchDataFoundToUpdate(String msg) {
        super(msg);
    }
}
