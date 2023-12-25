package ca.est.entity.http;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
/**
 * @author Estevam Meneses
 */
@Getter
@Setter
public class IdRequest {

	@NotNull
	private Long id;
	/**
	 * 
	 */
	public IdRequest() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @param id
	 */
	public IdRequest(Long id) {
		super();
		this.id = id;
	}	
}
