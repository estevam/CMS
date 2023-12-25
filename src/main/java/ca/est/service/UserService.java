package ca.est.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;

import ca.est.entity.UserBlog;
import ca.est.entity.http.ServiceResponse;
import ca.est.entity.http.UserCreateRequest;
import ca.est.entity.http.UserResponse;
import ca.est.entity.http.UserUpdateRequest;
import ca.est.exception.NoSuchElementFoundException;
import ca.est.repository.UserRepository;
import ca.est.util.RestUtil;

/**
 * @author Estevam Meneses
 */
@Service
public class UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private RestUtil restUtil;

	/**
	 * Find all userBlog
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse findAllUsers() throws NoSuchElementFoundException {
		List<UserBlog> userList = userRepository.findAll();
		TypeToken<List<UserResponse>> typeToken = new TypeToken<>() {
		};
		List<UserResponse> userResponList = modelMapper.map(userList, typeToken.getType());
		return new ServiceResponse(userResponList, HttpStatus.OK);
	}

	/**
	 * Find userBlog
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse findUser(Long id) throws NoSuchElementFoundException {
		if (id == null) {
			return new ServiceResponse(HttpStatus.BAD_REQUEST);
		}
		Optional<UserBlog> userBlog = userRepository.findById(id);
		if (userBlog.isPresent()) {
			UserResponse userRespon = modelMapper.map(userBlog.get(), UserResponse.class);
			return new ServiceResponse(userRespon, HttpStatus.OK);
		} else {
			return new ServiceResponse(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 *
	 * Update userBlog
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse updateUser(UserUpdateRequest userUpdateRequest) throws NoSuchElementFoundException {
		Optional<UserBlog> userBlog = userRepository.findById(userUpdateRequest.getId_user());
		if (userBlog.isPresent()) {
			UserBlog ub = userBlog.get();
			ub.setUsername(userUpdateRequest.getUsername());
			ub.setPassword(restUtil.createEncodePassword(userUpdateRequest.getPassword()));
			ub.setLastUpdate(LocalDateTime.now());
			userRepository.save(ub);
			return new ServiceResponse(HttpStatus.OK);
		} else {
			return new ServiceResponse(HttpStatus.NOT_FOUND);
		}
	}

	/**
	 *
	 * Create userBlog
	 * 
	 * @return
	 */
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse createUser(UserCreateRequest userCreateRequest) throws NoSuchElementFoundException {
		Optional<UserBlog> userBlog = userRepository.findByUsername(userCreateRequest.getUsername());
		if (userBlog.isEmpty()) {
			UserBlog ub = new UserBlog();
			ub.setUsername(userCreateRequest.getUsername());
			ub.setPassword(restUtil.createEncodePassword(userCreateRequest.getPassword()));
			ub.setCreated(LocalDateTime.now());
			userRepository.save(ub);
			return new ServiceResponse(HttpStatus.OK);
		} else {
			return new ServiceResponse(HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("{id}")
	@PreAuthorize("hasRole('ROLE_ADMIN') or hasRole('ROLE_EDITOR') or hasRole('ROLE_MANAGER') or hasRole('ROLE_USER')")
	public ServiceResponse deleteUser(Long id) {
		Optional<UserBlog> userBlog = userRepository.findById(id);
		if (userBlog.isPresent()) {
			UserBlog ub = userBlog.get();
			userRepository.delete(ub);
			return new ServiceResponse(HttpStatus.OK);
		} else {
			return new ServiceResponse(HttpStatus.NOT_FOUND);
		}
	}
}