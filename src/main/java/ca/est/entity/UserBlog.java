package ca.est.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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
@Table(name = "user_blog")
public class UserBlog {
	
	@Id
	@Column(name = "id_user")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_user;
	
	@Column(name = "username")
    private String username;
	
	@Column(name = "password")
    private String password;

	@Column(name = "last_update")
	private LocalDateTime lastUpdate;
	
	@Column(name = "created")
	private LocalDateTime created;
	
	@JsonManagedReference // Is the forward part of reference, the one that gets serialized normally
	@OneToMany(fetch = FetchType.LAZY, cascade= CascadeType.ALL)
	private Set<Article> article = new HashSet<Article>();

	@OneToOne(mappedBy = "userBlog", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private UserRole userRole = new UserRole();
	
 	public UserBlog() {
 		super();
 	}	
	/**
	 * 
	 * @param id_user
	 * @param username
	 * @param password
	 */
 	public UserBlog(Long id_user, String username, String password) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
	}    
	
	/**
	 * 
	 * @param id_user
	 * @param username
	 * @param password
	 * @param created
	 */
	public UserBlog(Long id_user, String username, String password, LocalDateTime created) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.created = created;
	}

	/**
	 * 
	 * @param id_user
	 * @param username
	 * @param password
	 * @param created
	 */
	public UserBlog(Long id_user, String username, String password, LocalDateTime created, LocalDateTime lastUpdate) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.created = created;
		this.lastUpdate = lastUpdate;
	}
	
	/**
	 * @param id_user
	 * @param username
	 * @param password
	 * @param token
	 * @param created
	 * @param article
	 */
	public UserBlog(Long id_user, String username, String password, LocalDateTime created,LocalDateTime lastUpdate,
			Set<Article> article) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.created = created;
		this.lastUpdate = lastUpdate;
		this.article = article;
	}

	/**
	 * @param id_user
	 * @param username
	 * @param password
	 * @param token
	 * @param created
	 * @param article
	 * @param userRole
	 */
	public UserBlog(Long id_user, String username, String password, LocalDateTime created,LocalDateTime lastUpdate,
			Set<Article> article, UserRole userRole) {
		super();
		this.id_user = id_user;
		this.username = username;
		this.password = password;
		this.created = created;
		this.lastUpdate = lastUpdate;
		this.article = article;
		this.userRole = userRole;
	}
	
}
