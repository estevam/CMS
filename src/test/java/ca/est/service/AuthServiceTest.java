package ca.est.service;

import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;

import ca.est.entity.http.LoginRequest;
import ca.est.entity.http.ServiceResponse;
import ca.est.util.JwtUtil;
import ca.est.util.RestUtil;

/**
 * @author Estevam Meneses
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class AuthServiceTest {

	@InjectMocks
	AuthService authService;

	@Spy
	JwtUtil jwtUtil;
 
	@Spy
	AuthenticationManager authenticationManager;

    @Spy
    RestUtil restUtil;

	MockHttpServletResponse response;
	MockHttpServletRequest request;

	@BeforeEach
	void init() {
		request = new MockHttpServletRequest();
		response = new MockHttpServletResponse();
	}
	
	@Test
	void login() {
		//ServiceResponse sr = authService.login(new LoginRequest("estevam", "estevam"), request, response);
		//Assert.assertEquals(sr.getStatus(), HttpStatus.OK);
	}
	
	@Test
	void loginUnauthorized() {
		ServiceResponse sr = authService.login(new LoginRequest("estevam", "error"), request, response);
		Assert.assertEquals(sr.getStatus(), HttpStatus.UNAUTHORIZED);
	}
}