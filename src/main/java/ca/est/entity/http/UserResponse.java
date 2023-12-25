package ca.est.entity.http;

import java.time.LocalDateTime;

import ca.est.entity.UserRole;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class UserResponse {

	private int id_user;
	private String username;
	private String password;
	private String token;
	private LocalDateTime created;
	private UserRole userRole = new UserRole();

	public UserResponse() {
		super();
	}

	public UserResponse(int id_user, String username, String password) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
	}

	public UserResponse(int id_user, String username, String password, String token) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.token = token;
	}

	public UserResponse(int id_user, String username, String password, String token, LocalDateTime created) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.token = token;
		this.created = created;
	}

	public UserResponse(int id_user, String username, String password, String token, LocalDateTime created,
			UserRole userRole) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.token = token;
		this.created = created;
		this.userRole = userRole;
	}
}