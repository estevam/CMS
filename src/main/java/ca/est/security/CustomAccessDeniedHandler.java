package ca.est.security;

import java.io.IOException;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.access.AccessDeniedHandler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
/**
 * @author Estevam Meneses
 */
@Log4j2
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

	@Override
	public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException, ServletException {
		// TODO Auto-generated method stub

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (authentication != null) {

			log.info("UserBlog '" + authentication.getName() + "' attempted to access the URL: " + request.getRequestURI());
		}
		response.sendRedirect(request.getContextPath() + "/access-denied");

	}
}
