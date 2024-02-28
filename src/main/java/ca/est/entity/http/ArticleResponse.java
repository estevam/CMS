package ca.est.entity.http;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class ArticleResponse {

	private Long id_article;
	private String title;
	private String text;
	private int status;
	private LocalDateTime created;
   // private UserCMS userCMS = new UserCMS();
	public ArticleResponse() {}
	/**
	 * Article Response
	 * @param id_articles
	 * @param title
	 * @param text
	 * @param status
	 * @param created
	 * @param userCMS
	 */
	public ArticleResponse(Long id_article, String title, String text, int status, LocalDateTime created) {
		super();
		this.id_article = id_article;
		this.title = title;
		this.text = text;
		this.status = status;
		this.created = created;
	}	
}