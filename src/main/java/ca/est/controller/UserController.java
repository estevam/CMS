/**
 * 
 */
package ca.est.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.est.annotation.TimerExecution;
import ca.est.entity.http.UserCreateRequest;
import ca.est.entity.http.UserUpdateRequest;
import ca.est.service.UserService;
import jakarta.validation.Valid;

/**
 * @author Estevam Meneses
 */
@RestController
@RequestMapping("/api")
public class UserController {

	@Autowired
	private UserService userService;

	@TimerExecution
	@GetMapping("/user/find")
	public ResponseEntity<?> findAllUsersAuth() {
		return userService.findAllUsers().build();
	}
	
	@TimerExecution
	@GetMapping("/user/find/{id}")
	public ResponseEntity<?> findAllUsers(@PathVariable Long id) {
		return userService.findUser(id).build();
	}
	
	@TimerExecution
	@PutMapping("/user/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UserUpdateRequest userUpdateRequest) {
		return userService.updateUser(userUpdateRequest).build();
	}
	
	@TimerExecution
	@PostMapping("/user/create")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserCreateRequest userCreateRequest) {
		return userService.createUser(userCreateRequest).build();
	}
	
	@TimerExecution
	@DeleteMapping("/user/delete/{id}")
	public ResponseEntity<?> deleteUser(@PathVariable("id") Long id) {
		return userService.deleteUser(id).build();
	}
}