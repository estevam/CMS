package ca.est.entity.http;

import jakarta.validation.constraints.Size;

/**
 * @author Estevam Meneses
 */
public class LoginRequest {
	
	@Size(min = 3, max = 100)
	private String username;
	
	@Size(min = 3, max = 50)
	private String password;
	
	public LoginRequest() {
	}

	/**
	 * @param username
	 * @param password
	 */
	public LoginRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}