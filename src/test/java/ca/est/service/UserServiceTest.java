/**
 * 
 */
package ca.est.service;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Optional;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import ca.est.entity.UserCMS;
import ca.est.entity.http.ServiceResponse;
import ca.est.entity.http.UserUpdateRequest;
import ca.est.repository.UserRepository;
import ca.est.util.RestUtil;

/**
 * @author Estevam Meneses
 */
@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserServiceTest {

	@InjectMocks
	UserService userService;

	@Mock
	UserRepository userRepository;

	@Mock
	RestUtil restUtil;

	@Spy
	ObjectMapper objectMapper;

	@Spy
	ModelMapper modelMapper;

	UserCMS userCMS = new UserCMS(1L, "Test", "Test");
	UserUpdateRequest userUpdateRequest = new UserUpdateRequest(1L, "Test", "Test");

	@Test
	void findAllArticlesNull() {
		ServiceResponse sr = userService.findUser(null);
		Assert.assertEquals(sr.getStatus(), HttpStatus.BAD_REQUEST);
	}

	@Test
	void findEmptyArticlesList() {
		ServiceResponse sr = userService.findAllUsers();
		Assert.assertEquals(sr.getStatus(), HttpStatus.OK);
	}

	@Test
	void findAllArticlesTest() {
		ArrayList<UserCMS> userList = new ArrayList<UserCMS>();
		userList.add(userCMS);
		ServiceResponse sr = userService.findAllUsers();
		Assert.assertEquals(sr.getStatus(), HttpStatus.OK);
	}

	@Test
	void updateArticlesTest() throws JsonProcessingException {

		when(userRepository.findById(1L)).thenReturn(Optional.of(userCMS));
		when(userRepository.save(userCMS)).thenReturn(userCMS);
		when(restUtil.createEncodePassword(userCMS.getPassword()))
				.thenReturn("$2a$10$hI/25ZBLc/aulJKtGOu.KeZvNbAuv1/fiJx2cjxTfY5.wZsOdbZgO");

		ServiceResponse sr = userService.updateUser(userUpdateRequest);

		Assert.assertEquals(sr.build().getStatusCode(), HttpStatus.OK);

		verify(userRepository, times(1)).save(userCMS);
	}
}