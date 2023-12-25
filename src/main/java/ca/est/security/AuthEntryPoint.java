package ca.est.security;


import java.io.IOException;
import java.util.Date;

import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.est.exception.ErrorResponse;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;
/**
 * @author Estevam Meneses
 */
@Log4j2
@Component
public class AuthEntryPoint implements AuthenticationEntryPoint {

  @Override
  public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException)
      throws IOException, ServletException {
    log.error("Unauthorized error: {}", authException.getMessage());

    ErrorResponse errorResponse = new ErrorResponse();
    
    response.setContentType(MediaType.APPLICATION_JSON_VALUE);
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

  
    errorResponse.setMessage("Unauthorized");
    errorResponse.setTimestamp(new Date());
    errorResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    
    final ObjectMapper mapper = new ObjectMapper();
    mapper.writeValue(response.getOutputStream(), errorResponse);
  }

}