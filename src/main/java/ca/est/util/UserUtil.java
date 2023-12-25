/**
 * 
 */
package ca.est.util;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import ca.est.entity.UserBlog;
import ca.est.security.UserDetailsBuilder;
import lombok.extern.log4j.Log4j2;

/**
 * @author Estevam Meneses
 */
@Log4j2
@Component
public class UserUtil {
	/**
	 * Get current userBlog on spring context
	 * 
	 * @return
	 */
	public UserBlog getCurrentUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			Object auth = authentication.getPrincipal();
			if (auth instanceof UserDetailsBuilder) {
				UserDetailsBuilder userDetailsBuilder = (UserDetailsBuilder) authentication.getPrincipal();
				return userDetailsBuilder.getUserBlog();
			}
		} catch (Exception e) {
			log.error("Failed to get internal userBlog", e);
		}
		return null;
	}

	/**
	 * Get current user roles
	 * @return
	 */
	public Set<String> getCurrentUserRoles() {

		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		try {
			Object auth = authentication.getPrincipal();
			if (auth instanceof UserDetailsBuilder) {
				UserDetailsBuilder userDetailsBuilder = (UserDetailsBuilder) authentication.getPrincipal();
				return userDetailsBuilder.getAuthorities().stream().map(item -> item.getAuthority())
						.collect(Collectors.toSet());
			}
		} catch (Exception e) {
			log.error("Failed to get internal userBlog", e);
		}
		return null;

	}
}
