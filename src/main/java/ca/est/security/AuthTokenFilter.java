package ca.est.security;

import java.io.IOException;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;

import org.springframework.web.filter.OncePerRequestFilter;

import ca.est.util.EncryptionUtil;
import ca.est.util.JwtUtil;
import ca.est.util.RestUtil;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.log4j.Log4j2;

/**
 * @author Estevam Meneses
 */
@Log4j2
public class AuthTokenFilter extends OncePerRequestFilter {

	@Autowired
	private JwtUtil jwtUtil;
	 
	@Autowired
	private RestUtil restUtil;
	  
	@Autowired
	private UserDetailsServiceImpl userDetailsService;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		if (request.getRequestURI().contains("/api/")) {
			try {
				//String headerAuth = request.getHeader("Authorization");
				
				String cookieAccessToken = restUtil.findCookieValueByName(JwtUtil.TokenType.access_token.get(), request);
				String accessToken = EncryptionUtil.decrypt(cookieAccessToken);
				
				if(StringUtils.isEmpty(accessToken)) {
					response.sendError(403);
					return;
				}
			
				if (jwtUtil.isValidToken(accessToken)) {
					String username = jwtUtil.getUsername(accessToken);

					UserDetails userDetails = userDetailsService.loadUserByUsername(username);
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
							userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

					SecurityContextHolder.getContext().setAuthentication(authentication);
	
				}
			} catch (Exception e) {
				log.error("Cannot set userBlog authentication: {}", e.getMessage());
				response.sendError(401);
				return;
			}
		}
		filterChain.doFilter(request, response);

	}

}
