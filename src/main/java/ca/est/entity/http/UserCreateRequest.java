package ca.est.entity.http;

import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class UserCreateRequest {
	
	@Size(min = 3, max = 100)
	private String username;
	
	@Size(min = 3, max = 50)
	private String password;

	public UserCreateRequest() {
		super();
	}

	public UserCreateRequest(String username, String password) {
		super();
		this.username = username;
		this.password = password;
	}
}