package ca.est.entity.http;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import ca.est.util.Pagination;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Estevam Meneses
 */
@Getter
@Setter
@JsonPropertyOrder({ "id_articles", "title", "text","status"})
public class ArticlePaginationRequest extends Pagination{
	
	private Long id_articles;
	private String title;
	private String text;
	private int status;
	
	public ArticlePaginationRequest(){
		super();
	}
	
	public ArticlePaginationRequest(String title, String text, int status) {
		super();
		this.title = title;
		this.text = text;
		this.status = status;
	}
	public ArticlePaginationRequest(Long id_articles, String title, String text, int status) {
		super();
		this.id_articles = id_articles;
		this.title = title;
		this.text = text;
		this.status = status;
	}	
}