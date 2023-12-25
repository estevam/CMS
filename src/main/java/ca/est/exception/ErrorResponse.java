package ca.est.exception;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;

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
public class ErrorResponse {

	// customizing timestamp serialization format
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
	private Date timestamp = new Date();
	private int status;
	private String message;
	private String stackTrace;
	private List<ValidationError> errors;

	@Getter
	@Setter
	@RequiredArgsConstructor
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