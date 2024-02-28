/**
 * 
 */
package ca.est.graphql;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import ca.est.annotation.TimerExecution;
import ca.est.entity.Article;
import ca.est.entity.http.ArticleRequest;
import ca.est.service.ArticleService;

/**
 * 
 * @author Estevam Meneses
 */
@Controller
public class ArticleGraphql{

	@Autowired
	private ArticleService articleService;

	/**
	 * Get article by id
	 * 
	 * @QueryMapping is a composite annotation to indicate the typeName as Query.
	 * @param id
	 * @return
	 */
    @QueryMapping(value = "articleById")
	@TimerExecution
	public Article articleById(@Argument Long id) {
		return (Article) articleService.findArticle(id).getData();
	}

	/**
	 * Get all articles
	 * 
	 * @SchemaMapping is used to map a handler method to a type and field pair. It
	 *                takes in two arguments: typeName and value.
	 * @param article
	 * @return
	 */
   @SchemaMapping(typeName = "Query", value = "allArticles")
	@TimerExecution
	public Collection<?> allArticles() {
		return articleService.findAllArticles().getCollection();
	}

	/**
	 * Update Article 
	 * 
	 * @MutationMapping is a composite annotation for Mutation types.
	 * 
	 * @param id_article
	 * @param title
	 * @param text
	 * @param status
	 * @return
	 */
	@MutationMapping
	@TimerExecution
	public Article updateArticle(@Argument Integer id_article, @Argument String title, @Argument String text,
			@Argument Integer status) {
		ArticleRequest ar = new ArticleRequest(Long.valueOf(id_article), title, text, status);
		return (Article) articleService.updateArticle(ar).getData();
	}

}