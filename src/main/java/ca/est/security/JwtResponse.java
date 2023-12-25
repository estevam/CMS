package ca.est.security;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
/**
 * @author Estevam Meneses
 */
@Data
public class JwtResponse {
	private String access_token;
	private String type = "Bearer";
	private String refresh_token;
	private Long id;
	private String username;
	private String email;
	private List<String> roles = new ArrayList<String>();

	public JwtResponse(String access_token, String refresh_token, Long id, String username, String email,
			List<String> roles) {
		this.access_token = access_token;
		this.refresh_token = refresh_token;
		this.id = id;
		this.username = username;
		this.email = email;
		this.roles = roles;
	}
}