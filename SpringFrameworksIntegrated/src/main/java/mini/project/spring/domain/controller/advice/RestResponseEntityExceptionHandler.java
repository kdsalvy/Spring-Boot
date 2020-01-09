package mini.project.spring.domain.controller.advice;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import mini.project.spring.domain.exception.NoContentException;

@RestControllerAdvice
public class RestResponseEntityExceptionHandler
		extends
			ResponseEntityExceptionHandler {

	@ExceptionHandler(value = NoContentException.class)
	protected ResponseEntity<Object> handleNoContentExceptions(Exception ex,
			WebRequest request) {
		return handleExceptionInternal(ex, "No Content Found",
				new HttpHeaders(), HttpStatus.NO_CONTENT, request);
	}

	@ExceptionHandler(value = ConstraintViolationException.class)
	protected ResponseEntity<Object> handleBadRequestExceptions(Exception ex,
			WebRequest request) {
		String errorCause = "Invalid Request";
		if (ex instanceof ConstraintViolationException) {
			errorCause = ((ConstraintViolationException) ex).getCause()
					.getMessage();
		}
		return handleExceptionInternal(ex, errorCause, new HttpHeaders(),
				HttpStatus.BAD_REQUEST, request);
	}
}
