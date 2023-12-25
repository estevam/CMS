/**
 * 
 */
package ca.est;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import ca.est.util.JwtUtil;

@ExtendWith(MockitoExtension.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class JwtUtilTest {

	@Mock
	JwtUtil jwtUtil;

	@BeforeEach
	void init() {
		MockitoAnnotations.openMocks(this);
		String secret = "0000001100101011100101110101101000101001010001011010000111010100000011101100011111110010001000101010010010101101111100010011100111011100110110101001111001110110100011001010100110101101010000010100110000110011011010111101001110010101110010010001000001101001";
		
		ReflectionTestUtils.setField(jwtUtil, "secret", secret);
		ReflectionTestUtils.setField(jwtUtil, "access_expiration_sc", 60);
		ReflectionTestUtils.setField(jwtUtil, "refresh_expiration_sc", 120);
	}

	@Test
	void myTest() {
		// when("this.secret").thenReturn("tests");
	//	String token = jwtUtil.generateToken("estevam");
	//	System.out.println(token);

		//
	}
}
