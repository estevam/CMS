package ca.est.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import ca.est.security.AuthEntryPoint;
import ca.est.security.AuthTokenFilter;
import ca.est.security.CustomAccessDeniedHandler;
import ca.est.security.UserDetailsServiceImpl;

/**
 * @author Estevam Meneses
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {
	@Autowired
	UserDetailsServiceImpl userDetailsServiceImpl;

	@Autowired
	AuthEntryPoint unauthorizedHandler;

    private static final String[] WHITE_LIST = {
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/v2/api-docs/**",
            "/swagger-resources/**",
            "/api-docs/**",
            "/swagger-ui.html",
            "/actuator/**",
            "/h2/**",
            "/login",
            "/logout",
            "/token/refresh",
            "/cms/graphql",
            "/cms/graphiql/**",
            "/cms/vendor/**",
            "/cms/vendor/graphiql/**"
           
    };
    
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	AuthTokenFilter authenticationJwtTokenFilter() {
		return new AuthTokenFilter();
	}

	@Bean
	DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsServiceImpl);
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	AccessDeniedHandler accessDeniedHandler() {
		return new CustomAccessDeniedHandler();
	}

	@Bean
	AuthenticationManager authenticationManager(AuthenticationConfiguration authConfig) throws Exception {
		return authConfig.getAuthenticationManager();
	}

	@Bean
	SecurityFilterChain filter(HttpSecurity http) throws Exception {

		// http.csrf(csrf -> csrf.disable())
		http.csrf(csrf -> csrf.ignoringRequestMatchers(WHITE_LIST).disable())
		        //FrameOptions is used to prevent security vulnerabilities like clickjacking. /
				//.headers(headers -> headers.frameOptions(FrameOptionsConfig::disable)) //Prevents the header from being added to the response.
		        .exceptionHandling(handling -> handling.accessDeniedHandler(accessDeniedHandler()) // Exception handling
						.authenticationEntryPoint(unauthorizedHandler))
				.sessionManagement(management -> management.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
				.authorizeHttpRequests(
						auth -> auth.requestMatchers(WHITE_LIST).permitAll().anyRequest().authenticated())
				.logout(logout -> logout.disable());

		http.authenticationProvider(authenticationProvider());
		http.securityMatcher("/api/**").addFilterBefore(authenticationJwtTokenFilter(),UsernamePasswordAuthenticationFilter.class);
		//Extra filters example
		//http.addFilterAfter(authenticationJwtTokenFilter(), BasicAuthenticationFilter.class);
		//http.addFilterBefore(authenticationJwtTokenFilter(), BasicAuthenticationFilter.class);
		//http.addFilterAt(authenticationJwtTokenFilter(), BasicAuthenticationFilter.class);
		return http.build();
	}
}