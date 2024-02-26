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
@Table(name = "user_role")
public class UserRole {
	@Id
	@Column(name = "id_role")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id_role;
	
	@Column(name = "role")
    private String role;	
	
	@Column(name = "created")
	private LocalDateTime created;
  
	@JsonBackReference //is the back part of reference; it’ll be omitted from serialization.
	@OneToOne(cascade = { CascadeType.PERSIST, CascadeType.REMOVE })
	@JoinColumn(name = "id_user")
    private UserCMS userCMS;
    
	public UserRole() {
		super();
	}
	
	/**
	 * @param id_role
	 * @param role
	 * @param created
	 */
	public UserRole(Long id_role, String role, LocalDateTime created) {
		super();
		this.id_role = id_role;
		this.role = role;
		this.created = created;
	}
    
	/**
	 * @param id_role
	 * @param role
	 * @param created
	 * @param userCMS
	 */
	public UserRole(Long id_role, String role, LocalDateTime created, UserCMS userCMS) {
		super();
		this.id_role = id_role;
		this.role = role;
		this.created = created;
		this.userCMS = userCMS;
	}   
}