package ca.est.entity.http;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import ca.est.entity.Article;
import lombok.Data;

/**
 * @author Estevam Meneses
 */
@Data
public class UserArticleResponse {

	private int id_user;
	private String username;
	private String password;
	private String token;
	private LocalDateTime created;
	private Set<Article> article = new HashSet<Article>();

	public UserArticleResponse() {
		super();
	}

	public UserArticleResponse(int id_user, String username, String password) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
	}

	public UserArticleResponse(int id_user, String username, String password, String token) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.token = token;
	}

	public UserArticleResponse(int id_user, String username, String password, String token, LocalDateTime created) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.token = token;
		this.created = created;
	}
}
