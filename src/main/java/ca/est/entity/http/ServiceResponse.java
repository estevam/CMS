package ca.est.entity.http;

import java.util.Objects;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMap;
import org.springframework.util.ObjectUtils;

/**
 * Service response will encapsulate all the Service results to be used by
 * RestControllers
 * 
 * @author Estevam Meneses
 * 
 */
public class ServiceResponse {
	private HttpStatus status;
	private Object data;
	private MultiValueMap<String, String> header;

	public ServiceResponse() {
	}

	/**
	 * Instantiate ResponseEntity.class using HttpStatus
	 * 
	 * @param status
	 */
	public ServiceResponse(HttpStatus status) {
		this.status = status;
	}

	/**
	 * Instantiate ResponseEntity.class using HttpStatus and HttpBody
	 * 
	 * @param status
	 * @param body
	 */
	public ServiceResponse(Object data, HttpStatus status) {
		this.status = status;
		this.data = data;
	}

	/**
	 * Instantiate ResponseEntity.class using HttpStatus, HttpBody and HttpHeaders
	 * 
	 * @param httpStatus
	 * @param httpBody
	 * @param httpHeaders
	 */
	public ServiceResponse(Object data, HttpStatus status, MultiValueMap<String, String> header) {
		this.status = status;
		this.data = data;
		this.header = header;
	}

	/**
	 * Get Spring enum HTTP status
	 * 
	 * @return
	 */
	public HttpStatus getStatus() {
		return status;
	}

	/**
	 * Result created by the service
	 * 
	 * @return
	 */
	public Object getData() {
		return data;
	}

	/**
	 * Get HTTP headers
	 * 
	 * @return
	 */
	public MultiValueMap<?, ?> getHeader() {
		return header;
	}

	/**
	 * Create Response entity using Data, HTTP status and Headers
	 * 
	 * @return ResponseEntity<?>
	 */
	public ResponseEntity<?> build() {
		if (!ObjectUtils.isEmpty(header)) {
			return new ResponseEntity<>(data, header, status);

		}
		return new ResponseEntity<>(data, status);
	}

	/**
	 * Create Response entity using Data, HTTP status and Headers
	 * 
	 * @return ResponseEntity<?>
	 */
	public ResponseEntity<?> buildWithHeaders() {
		return new ResponseEntity<>(data, header, status);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ServiceResponse other = (ServiceResponse) obj;
		return Objects.equals(data, other.data) && Objects.equals(header, other.header) && status == other.status;
	}

	@Override
	public int hashCode() {
		return Objects.hash(data, header, status);
	}

	@Override
	public String toString() {
		return "ServiceResponse [status=" + status + ", data=" + data + ", header=" + header + "]";
	}
}