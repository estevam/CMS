package ca.est.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ca.est.entity.UserBlog;
import ca.est.repository.UserRepository;
import jakarta.transaction.Transactional;
/**
 * @author Estevam Meneses
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	  
	  @Autowired
	  UserRepository userRepository;
	  
	  @Override
	  @Transactional
	  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
	    UserBlog userBlog = userRepository.findByUsername(username)
	        .orElseThrow(() -> new UsernameNotFoundException("UserBlog Not Found with username: " + username));

	    return UserDetailsBuilder.build(userBlog);
	  }
}