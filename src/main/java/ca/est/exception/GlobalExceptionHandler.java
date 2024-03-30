package ca.est.exception;

import java.util.Objects;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.lang.Nullable;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Estevam Meneses
 */
@Slf4j(topic = "EXCEPTION_HANDLER")
@Order(Ordered.HIGHEST_PRECEDENCE)
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

	@Value("${reflectoring.trace:false}")
	private boolean printStackTrace;
	public static final String TRACE = "trace";

	@ExceptionHandler(EntityNotFoundException.class)
	protected ResponseEntity<Object> handleEntityNotFound(EntityNotFoundException ex) {
		return buildErrorResponse(ex, "Entity not found", HttpStatus.NOT_FOUND);
	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		return buildErrorResponse(ex, "Malformed JSON request", HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@Override
	@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("UNPROCESSABLE_ENTITY");

		for (FieldError fieldError : ex.getBindingResult().getFieldErrors()) {
			errorResponse.addValidationError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		return ResponseEntity.unprocessableEntity().body(errorResponse);
	}

	@ExceptionHandler(NoSuchElementFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public ResponseEntity<Object> handleNoSuchElementFoundException(NoSuchElementFoundException itemNotFoundException,
			WebRequest request) {
		log.error("Failed to find the requested element", itemNotFoundException);
		return buildErrorResponse(itemNotFoundException, HttpStatus.NOT_FOUND, request);
	}

	@ExceptionHandler(Exception.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ResponseEntity<Object> handleAllUncaughtException(Exception exception, WebRequest request) {
		log.error("Unknown error occurred", exception);
		return buildErrorResponse(exception, "Unknown error occurred", HttpStatus.INTERNAL_SERVER_ERROR, request);
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.FORBIDDEN)
	public ResponseEntity<Object> handleUserNotFoundException(Exception exception, WebRequest request) {
		log.error("User not found exception", exception);
		return buildErrorResponse(exception, "User not found exception", HttpStatus.FORBIDDEN, request);
	}

	@Override
	public ResponseEntity<Object> handleExceptionInternal(Exception ex, @Nullable Object body, HttpHeaders headers,
			HttpStatusCode statusCode, WebRequest request) {
		return buildErrorResponse(ex, HttpStatus.valueOf(statusCode.value()), request);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus) {
		return buildErrorResponse(exception, message, httpStatus, null);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception, HttpStatus httpStatus, WebRequest request) {
		return buildErrorResponse(exception, exception.getMessage(), httpStatus, request);
	}

	private ResponseEntity<Object> buildErrorResponse(Exception exception, String message, HttpStatus httpStatus,
			WebRequest request) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(message);
		errorResponse.setStatus(httpStatus.value());
		if (printStackTrace && isTraceOn(request)) {
			errorResponse.setStackTrace(ExceptionUtils.getStackTrace(exception));
		}
		return ResponseEntity.status(httpStatus).body(errorResponse);
	}

	private boolean isTraceOn(WebRequest request) {
		String[] value = request.getParameterValues(TRACE);
		return Objects.nonNull(value) && value.length > 0 && value[0].contentEquals("true");
	}

}