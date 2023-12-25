/**
 * 
 */
package ca.est.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.est.entity.http.LoginRequest;
import ca.est.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

/**
 * @author Estevam Meneses
 */
@RestController
public class AuthController {
	  
	  @Autowired
	  private AuthService authService;
 
	  @PostMapping("/login")
	  public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest, HttpServletResponse res, HttpServletRequest req) {
		  return authService.login(loginRequest,res,req).build();
	  }
	  	
	  @PostMapping("/logout")
	  public ResponseEntity<?> logout(HttpServletResponse res, HttpServletRequest req) {
		  return authService.logout(res, req).build();
	  }
	  
	  @PostMapping("/token/refresh")
	  public ResponseEntity<?> refresh(HttpServletResponse res, HttpServletRequest req) {
		  return authService.refresh(res, req).build();
	  }
}