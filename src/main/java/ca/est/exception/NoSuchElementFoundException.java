package ca.est.exception;

/**
 * @author Estevam Meneses
 */
public class NoSuchElementFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public NoSuchElementFoundException(String message) {
		super(message);
	}
}
