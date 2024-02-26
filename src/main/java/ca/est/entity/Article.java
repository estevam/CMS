package ca.est.entity;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Estevam Meneses
 */
@Getter
@Setter
@Entity
@Table(name = "article")
public class Article {
	
	@Id
	@Column(name = "id_articles")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id_articles;
	
	@Column(name = "title")
	private String title;
	
	@Column(name = "text")
	private String text;
	
	@Column(name = "status")
	private int status;
	
	@Column(name = "created")
	private LocalDateTime created;

	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "id_user")
	@JsonBackReference //is the back part of reference; it’ll be omitted from serialization.
    private UserCMS userCMS;
	
	public Article() {
		super();
	}

	public Article(Long id_articles, String title, String text, int status, LocalDateTime created) {
		super();
		this.id_articles = id_articles;
		this.title = title;
		this.text = text;
		this.status = status;
		this.created = created;
	}

	/**
	 * @param id_articles
	 * @param title
	 * @param text
	 * @param status
	 * @param created
	 * @param id_user
	 */
	public Article(Long id_articles, String title, String text, int status, LocalDateTime created, UserCMS userCMS) {
		super();
		this.id_articles = id_articles;
		this.title = title;
		this.text = text;
		this.status = status;
		this.created = created;
		this.userCMS = userCMS;
	}
}
