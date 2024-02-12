package ca.est.entity.http;

import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class ArticleCreateRequest {
	
	private String title;
	private String text;
	private int status;
	
	
	public ArticleCreateRequest(String title, String text, int status) {
		super();
		this.title = title;
		this.text = text;
		this.status = status;
	}
}