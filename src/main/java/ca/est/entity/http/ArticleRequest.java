package ca.est.entity.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class ArticleRequest {
	private Long id_articles;
	private String title;
	private String text;
	private int status;
	
	
	public ArticleRequest(String title, String text, int status) {
		super();
		this.title = title;
		this.text = text;
		this.status = status;
	}
	public ArticleRequest(Long id_articles, String title, String text, int status) {
		super();
		this.id_articles = id_articles;
		this.title = title;
		this.text = text;
		this.status = status;
	}	
}