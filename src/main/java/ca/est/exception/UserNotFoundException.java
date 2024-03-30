package ca.est.exception;
/**
 * @author Estevam Meneses
 */
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class UserNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;
	public UserNotFoundException(String message) {
        super(message);
    }
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
