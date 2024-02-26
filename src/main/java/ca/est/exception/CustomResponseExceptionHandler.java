package ca.est.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import ca.est.entity.UserCMS;
import ca.est.util.UserUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
/**
 * @author Estevam Meneses
 */
@Log4j2
@ControllerAdvice
public class CustomResponseExceptionHandler extends ResponseEntityExceptionHandler {
	@Autowired
	private UserUtil userUtil;
	/**
	 * ResponseEntityExceptionHandler
	 * 
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(AccessDeniedException.class)
	public final ResponseEntity<ErrorResponse> handleAccessDeniedException(AccessDeniedException ex,
			HttpServletRequest request) {
		UserCMS userCMS = userUtil.getCurrentUser();
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage(ex.getMessage());
		errorResponse.setStatus(HttpStatus.FORBIDDEN.value());
		log.warn("Access denied by the userBlog {} id:{}", userCMS.getUsername(), userCMS.getId_user());
		log.warn("Remote userBlog:{} URL:{} Method:{} ", request.getRemoteUser(), request.getRequestURL().toString() ,request.getMethod());		
		return new ResponseEntity<>(errorResponse, HttpStatus.FORBIDDEN);
	}
}
