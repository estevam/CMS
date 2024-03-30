package ca.est.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonRootName;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
@RequiredArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
//@JsonPropertyOrder({ "name", "id" })
@JsonRootName(value = "errors")
public class ErrorResponse {

	// customizing timestamp serialization format
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private LocalDateTime timestamp = LocalDateTime.now();
	private int status;
	private String message;
    @JsonAlias("stack_trace")
	private String stackTrace;
	private List<ValidationError> errors;

	public ErrorResponse(HttpStatus status) {
		this.status = status.value();
	}
	
	public ErrorResponse(HttpStatus status, String message) {
		this.status = status.value();
		this.message = message;
	}	
	
	@Getter
	@Setter
	@RequiredArgsConstructor
	@JsonRootName(value = "validation_error")
	private static class ValidationError {
		private final String field;
		private final String message;
	}

	public void addValidationError(String field, String message) {
		if (Objects.isNull(errors)) {
			errors = new ArrayList<ValidationError>();
		}
		errors.add(new ValidationError(field, message));
	}
}