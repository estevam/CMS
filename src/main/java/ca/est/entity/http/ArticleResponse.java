package ca.est.entity.http;

import java.time.LocalDateTime;

import ca.est.entity.UserBlog;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class ArticleResponse {

	private Long id_articles;
	private String title;
	private String text;
	private int status;
	private LocalDateTime created;
    private UserBlog userBlog = new UserBlog();

	/**
	 * Article Response
	 * @param id_articles
	 * @param title
	 * @param text
	 * @param status
	 * @param created
	 * @param userBlog
	 */
	public ArticleResponse(Long id_articles, String title, String text, int status, LocalDateTime created,
			UserBlog userBlog) {
		super();
		this.id_articles = id_articles;
		this.title = title;
		this.text = text;
		this.status = status;
		this.created = created;
		this.userBlog = userBlog;
	}	
}