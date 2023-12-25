package ca.est.entity.http;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class UserUpdateRequest {

	@NotNull
	private Long id_user;
	
	@Size(min = 3, max = 100)
	private String username;
	
	@Size(min = 3, max = 50)
	private String password;

	public UserUpdateRequest() {
		super();
	}

	public UserUpdateRequest(Long id_user, String username, String password) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
	}
}