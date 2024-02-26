package ca.est.entity.http;

import java.time.LocalDateTime;

import ca.est.entity.UserCMS;
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
    private UserCMS userCMS = new UserCMS();

	/**
	 * Article Response
	 * @param id_articles
	 * @param title
	 * @param text
	 * @param status
	 * @param created
	 * @param userCMS
	 */
	public ArticleResponse(Long id_articles, String title, String text, int status, LocalDateTime created,
			UserCMS userCMS) {
		super();
		this.id_articles = id_articles;
		this.title = title;
		this.text = text;
		this.status = status;
		this.created = created;
		this.userCMS = userCMS;
	}	
}