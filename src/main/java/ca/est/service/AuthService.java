package ca.est.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import ca.est.annotation.TimerExecution;
import ca.est.entity.http.LoginRequest;
import ca.est.entity.http.ServiceResponse;
import ca.est.util.EncryptionUtil;
import ca.est.util.JwtUtil;
import ca.est.util.JwtUtil.TokenType;
import ca.est.util.RestUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Estevam Meneses
 */
//@Log4j2
@Service
public class AuthService {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	@Autowired
	private RestUtil restUtil;

	/**
	 * User authentication
	 * 
	 * @param login
	 * @param response
	 * @param request
	 * @return
	 */
	@TimerExecution
	public ServiceResponse login(LoginRequest login, HttpServletResponse response, HttpServletRequest request) {

		try {
			Authentication authentication = authenticationManager
					.authenticate(new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword()));
			SecurityContextHolder.getContext().setAuthentication(authentication);

			String accessToken = jwtUtil.generateToken(login.getUsername(), TokenType.access_token);
			String refreshToken = jwtUtil.generateToken(login.getUsername(), TokenType.refresh_token);

			response = restUtil.addCookie(accessToken, refreshToken, response, request);

			return new ServiceResponse(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			// log.error("Login error:{}", e);
			return new ServiceResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	/**
	 * Logout Remove tokens from the cookie
	 * 
	 * @param response
	 * @param request
	 * @return
	 */
	@TimerExecution
	public ServiceResponse logout(HttpServletResponse response, HttpServletRequest request) {
		try {
			response = restUtil.deleteCookie(request, response);
			return new ServiceResponse(HttpStatus.OK);
		} catch (Exception e) {
			// TODO: handle exception
			// log.error("Logout error:{}", e);
			return new ServiceResponse(e.getMessage(), HttpStatus.UNAUTHORIZED);
		}
	}

	@TimerExecution
	public ServiceResponse refresh(HttpServletResponse response, HttpServletRequest request) {
		try {
			String cookieAccessToken = restUtil.findCookieValueByName(JwtUtil.TokenType.access_token.get(), request);
			String cookieRefreshToken = restUtil.findCookieValueByName(JwtUtil.TokenType.access_token.get(), request);

			if (StringUtils.isEmpty(cookieAccessToken) || StringUtils.isEmpty(cookieRefreshToken)) {
				return new ServiceResponse(HttpStatus.FORBIDDEN);
			}

			String accessToken = EncryptionUtil.decrypt(cookieAccessToken);
			String accessRefresh = EncryptionUtil.decrypt(cookieRefreshToken);

			if (StringUtils.isEmpty(accessToken) || StringUtils.isEmpty(accessRefresh)) {
				return new ServiceResponse(HttpStatus.FORBIDDEN);
			}

			if (jwtUtil.isValidToken(accessRefresh)) {
				String username = jwtUtil.getUsername(accessToken);
				String newAccessToken = jwtUtil.generateToken(username, TokenType.access_token);
				cookieRefreshToken = EncryptionUtil.encrypt(newAccessToken);
				response = restUtil.updateCookieAccessToken(cookieRefreshToken, request, response);
				return new ServiceResponse(HttpStatus.OK);
			} else {
				return new ServiceResponse(HttpStatus.FORBIDDEN);
			}

		} catch (Exception e) {
			// TODO: handle exception
			// log.error("Refresh token error:{}", e);
		}
		return new ServiceResponse(HttpStatus.UNAUTHORIZED);
	}
}