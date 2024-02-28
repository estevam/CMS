package ca.est.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.graphql.server.webmvc.GraphiQlHandler;
import org.springframework.web.servlet.function.RouterFunction;
import org.springframework.web.servlet.function.RouterFunctions;
import org.springframework.web.servlet.function.ServerResponse;

/**
 * @author Estevam Meneses
 */
@Configuration(proxyBeanMethods = false)
public class GraphQLConfig {
    @Bean
    @Order(0)
    RouterFunction<ServerResponse> graphiQlRouterFunction() {
        RouterFunctions.Builder builder = RouterFunctions.route();
        ClassPathResource graphiQlPage = new ClassPathResource("cms/graphiql/index.html"); 
        GraphiQlHandler graphiQLHandler = new GraphiQlHandler("/cms/graphql", "", graphiQlPage); 
        builder = builder.GET("/cms/graphiql", graphiQLHandler::handleRequest); 
        return builder.build(); 
    }
}
