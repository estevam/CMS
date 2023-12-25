/**
 * 
 */
package ca.est.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @author Estevam Meneses
 */
@Configuration
@PropertySource("classpath:config.properties")
public class BlogConfig {
	
    @Bean
    CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new
                UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
       config.addAllowedOrigin("*");
        //log.info("Added CORS allowed patterns: '{}' ", String.join("', '", yamlConfig.getCorsAllowedList()));
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }
    
    /**
     * ModelMapper object conversion 
     * @return
     */
    @Bean
    ModelMapper modelMapper() {
      return new ModelMapper();
    }
}
