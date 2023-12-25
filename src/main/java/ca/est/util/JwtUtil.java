package ca.est.util;

import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Date;
import java.util.UUID;

import javax.crypto.SecretKey;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

/**
 * 
 * @author Estevam Meneses https://github.com/jwtk/jjwt?tab=readme-ov-file
 */
@Component
public class JwtUtil {

	@Value("${jwt.secret}")
	public String secret;

	@Value("${jwt.access_expiration_sc}")
	private Integer access_expiration_sc;

	@Value("${jwt.refresh_expiration_sc}")
	private Integer refresh_expiration_sc;

	public enum TokenType {
		access_token("access_token"), refresh_token("refresh_token");

		private String item;

		TokenType(String str) {
			this.item = str;
		}

		public String get() {
			return item;
		}
	}

	/**
	 * 
	 * @return
	 */
	private SecretKey getSigningKey() {
		return Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	}

	public String generateToken(String username, TokenType tokenType) {
		String uuid = UUID.randomUUID().toString(); // Used to set the JWT unique
		Date now = new Date();
		Date exp = null;
		if (tokenType.get().equals(TokenType.access_token.get())) {
			// access token
			exp = Date.from(Instant.now().plusSeconds(access_expiration_sc));
		} else {
			// refresh token
			exp = Date.from(Instant.now().plusSeconds(refresh_expiration_sc));
		}

		return Jwts.builder().claim("username", username).claim("token_type", tokenType.get()).id(uuid).issuedAt(now)
				.expiration(exp).signWith(getSigningKey()).compact();
	}

	/**
	 * Get username
	 * 
	 * @param token
	 * @return
	 */
	public String getUsername(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().get("username")
				.toString();
	}

	/**
	 * Get token type
	 * 
	 * @param token
	 * @return TokenType
	 */
	public TokenType getTokenType(String token) {
		String item = Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload()
				.get("token_type").toString();
		return item.equals(TokenType.access_token.get()) ? TokenType.access_token : TokenType.refresh_token;
	}

	/**
	 * Check if token have valid user and is not expired
	 * 
	 * @param token
	 * @return
	 */
	public boolean isValidToken(String token) {
		String username = this.getUsername(token);
		return StringUtils.isNotEmpty(username) && !isTokenExpired(token);
	}

	/**
	 * Check if token is expired
	 * 
	 * @param token
	 * @return
	 */
	private boolean isTokenExpired(String token) {
		return Jwts.parser().verifyWith(getSigningKey()).build().parseSignedClaims(token).getPayload().getExpiration()
				.before(new Date());
	}
}
