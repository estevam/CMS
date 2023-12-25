package ca.est.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Formatter;
import java.util.UUID;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 *
 * @author Estevam.Meneses
 */
@Log4j2
@Component
public class RestUtil {
	
	@Value("${access_token}")
	private String access_token;

	@Value("${refresh_token}")
	private String refresh_token;

	@Value("${cookie_path}")
	private String cookie_path;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private PasswordEncoder encoder;
	
	private static String OS = System.getProperty("os.name").toLowerCase();
	
	/**
	 * Encode string StandardCharsets.UTF_8
	 *
	 * @param value
	 * @return
	 */
	public String encodeValue(String value) {
		try {
			return URLEncoder.encode(value, StandardCharsets.UTF_8.toString());
		} catch (UnsupportedEncodingException e) {
			log.error("Exception found when encode {}", value, e);
			return null;
		}
	}

	/**
	 * Get cookie by name
	 *
	 * @param cookieName
	 * @param request
	 * @return
	 */
	public String findCookieValueByName(String cookieName, HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		String cookieValue = null;
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					cookieValue = cookie.getValue();
				}
			}
		}
		return cookieValue;
	}

	/**
	 * Update cookie access token 
	 *
	 * @param accessToken
	 * @param request
	 * @param response
	 * @return
	 */
	public HttpServletResponse updateCookieAccessToken(String accessToken, HttpServletRequest request,
			HttpServletResponse response) {

		boolean accessTokenExist = false;

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(access_token)) {
					cookie.setPath(cookie_path);
					cookie.setMaxAge(EnumerationUtil.TimeSecond.FOUR_HOURS.getTime());
					cookie.setValue(accessToken);
					cookie.setHttpOnly(true);
				
					if (!isWindows() || !isMac()) { // DEV
						cookie.setSecure(false);
		
					}
					
					if(isLinux()) {// PROD
						cookie.setSecure(true);
					}
					response.addCookie(cookie);
					accessTokenExist = true;
				}
			}
			if (!accessTokenExist) {
				Cookie cookieAT = new Cookie(access_token, accessToken);
				cookieAT.setPath(cookie_path);
				cookieAT.setMaxAge(EnumerationUtil.TimeSecond.FOUR_HOURS.getTime());
				cookieAT.setHttpOnly(true);
		
				if (!isWindows() || !isMac()) { // DEV
					cookieAT.setSecure(false);
	
				}
				
				if(isLinux()) {// PROD
					cookieAT.setSecure(true);
				}
				
				response.addCookie(cookieAT);
			}
		}
		return response;
	}
	/*
	 * Add cookie 
	 * Set cookie secure only for production
	 * Access and refresh token will be encrypt
	 * @param accessToken 
	 * @param refreshToken
	 * @param csrf
	 * @param response
	 * @return
	 */
	public HttpServletResponse addCookie(String accessToken, String refreshToken,
			HttpServletResponse response, HttpServletRequest request) {
		
		accessToken = EncryptionUtil.encrypt(accessToken);
		refreshToken = EncryptionUtil.encrypt(refreshToken);
		response = deleteCookie(request, response); // before create tokens remove all cookies

		//Creating access_token cookie
		Cookie cookieAT = new Cookie(access_token, accessToken);
		cookieAT.setPath(cookie_path);
		cookieAT.setMaxAge(EnumerationUtil.TimeSecond.FOUR_HOURS.getTime());
		cookieAT.setHttpOnly(true); //hidden from client side
		
		//Creating refresh_token cookie
		Cookie cookieRT = new Cookie(refresh_token, refreshToken);
		cookieRT.setPath(cookie_path);
		cookieRT.setMaxAge(EnumerationUtil.TimeSecond.FOUR_HOURS.getTime());
		cookieRT.setHttpOnly(true);//hidden from client side

		if (!isWindows() || !isMac()) { // DEV
			cookieAT.setSecure(false);
			cookieRT.setSecure(false);
		}
		
		if(isLinux()) {// PROD
			cookieRT.setSecure(true);
			cookieAT.setSecure(true);
		}

		response.addCookie(cookieAT);
		response.addCookie(cookieRT);

		return response;
	}
 
	/*
	 * Remove cookie by name
	 *
	 * @param cookieName
	 * @param request
	 * @param response
	 * @return
	 */
	public HttpServletResponse deleteCookieByName(String cookieName, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(cookieName)) {
					response.addCookie(resetCookie(cookie));
				}
			}
		}
		return response;
	}

	/**
	 * Delete access_token, refresh_token , csrf_cookie_name
	 *
	 * @param request
	 * @param response
	 * @return
	 */
	public HttpServletResponse deleteCookie(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals(access_token)) {
					response.addCookie(resetCookie(cookie));
				} else if (cookie.getName().equals(refresh_token)) {
					response.addCookie(resetCookie(cookie));
				} 
			}
		}
		return response;
	}

	/**
	 * Delete cookie
	 *
	 * @param cookie
	 * @return
	 */
	private Cookie resetCookie(Cookie cookie) {
		cookie.setPath(cookie_path);
		cookie.setMaxAge(0);
		cookie.setValue("");

		if (!isLinux()) {// PROD
			cookie.setSecure(true);
		} else {
			cookie.setSecure(false);
		}
		return cookie;
	}

	/*
	 * Create JsonNode
	 *
	 * @param json
	 * @return
	 */
	public JsonNode jsonReader(String json) {
		try {
			return objectMapper.readTree(json);
		} catch (JsonProcessingException e) {
			log.error("Failed to read from json string {}", json, e);
		}
		return null;
	}

	/**
	 * Write object as json
	 *
	 * @param obj
	 * @return
	 */
	public String writeObjectAsJson(Object obj) {
		try {
			return objectMapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			log.error("Failed to converting json object", e);
			return null;
		}
	}

	public String findItemJson(String data, String findItem) {
		JsonObject json = new Gson().fromJson(data, JsonObject.class);
		// log.error(" {}" , data.toString());
		try {
			JsonElement je = json.get(findItem);
			return je.getAsString();
		} catch (Exception e) {
			log.error("Failed to get field {} from data {}", findItem, data, e);
			return null;
		}
	}

	public boolean isWindows() {
		try {
			return (OS.indexOf("win") >= 0);
		} catch (Exception ex) {
			log.error("Failed to get windows indicator", ex);
			return false;
		}
	}
	
	public boolean isMac() {
		try {
			return (OS.indexOf("mac os x") >= 0);
		} catch (Exception ex) {
			log.error("Failed to get linux indicator", ex);
			return false;
		}
	}

	public boolean isLinux() {
		try {
			return (OS.indexOf("nix") >= 0 || OS.indexOf("nux") >= 0 || OS.indexOf("aix") > 0);
		} catch (Exception ex) {
			log.error("Failed to get linux indicator", ex);
			return false;
		}
	}
	
	/**
	 * Byte to Hex String
	 *
	 * @param bytes
	 * @return
	 */
	public String bytesToHex(byte[] bytes) {
		StringBuilder sb = new StringBuilder(bytes.length * 2);
		@SuppressWarnings("resource")
		Formatter formatter = new Formatter(sb);
		for (byte b : bytes) {
			formatter.format("%02x", b);
		}
		return sb.toString();
	}
	
	/**
	 * Create spring security password encoder level 10
	 * @param item
	 * @return BCryptPasswordEncoder string
	 */
	public String createEncodePassword(String item) {
		return encoder.encode(item);
	}
	
	/**
	 * Get random letters and numbers with length 200 Using Apache commons lang3
	 */
	public String createApplicationKey() {
		return RandomStringUtils.random(200, true, true);
	}

	/**
	 * Generate randon string by length Length of random string
	 *
	 * @param length
	 * @return
	 */
	public String generateRandonString(int length) {
		return RandomStringUtils.random(length, true, false);
	}

	/**
	 * Generate token
	 *
	 * @return
	 */
	public String generateToken() {
		MessageDigest salt = null;
		try {
			salt = MessageDigest.getInstance("SHA-256");
			salt.update(UUID.randomUUID().toString().getBytes("UTF-8"));
		} catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
			log.error("Failed on updating device token", e);
		}

		return bytesToHex(salt.digest());
	}

	/**
	 * Get UUID
	 *
	 * @return
	 */
	public String getUUID() {
		return UUID.randomUUID().toString();
	}
	
	/**
	 * Get HttpServletRequest from context holder.
	 * @return HttpServletRequest
	 */
	public HttpServletRequest getHttpServletRequest() {
		final ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
	    return requestAttributes.getRequest();
	}
	
	/**
	 * Get HttpServletResponse from context holder.
	 * @return HttpServletResponse
	 */
	public HttpServletResponse getHttpServletResponse() {
		try {
			final ServletRequestAttributes requestAttributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
		    return requestAttributes.getResponse();
		} catch (Exception e) {
			log.error("Error on get Http servlet response:", e);
			return null;
		}
	}
}