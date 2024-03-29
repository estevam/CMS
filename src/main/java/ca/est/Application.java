package ca.est;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Estevam Meneses
 */
@ComponentScan("ca.est")
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
		System.out.println("*************************************************************");
		System.out.println("Database H2 -> http://localhost:8080/cms/h2");
		System.out.println("Swagger  UI -> http://localhost:8080/cms/swagger-ui/index.html");
		System.out.println("OpenAPI yaml ->http://localhost:8080/cms/api-docs");
		System.out.println("Graphiql -> http://localhost:8080/cms/graphiql?path=/cms/graphql");
		System.out.println("*************************************************************");
	}
}
