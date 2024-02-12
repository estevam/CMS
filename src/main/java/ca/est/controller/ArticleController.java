/**
 * 
 */
package ca.est.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ca.est.annotation.TimerExecution;
import ca.est.entity.http.ArticleCreateRequest;
import ca.est.entity.http.ArticlePaginationRequest;
import ca.est.entity.http.ArticleRequest;
import ca.est.service.ArticleService;
import jakarta.validation.Valid;

/**
 * @author Estevam Meneses
 */
@RestController
@RequestMapping("/api")
public class ArticleController {

	@Autowired
	private ArticleService articleService;

	@TimerExecution
	@GetMapping("/article/find")
	public ResponseEntity<?> findAllArticle() {
		return articleService.findAllArticles().build();
	}
	
	@TimerExecution
	@PostMapping("/article/find")
	public ResponseEntity<?> findArticleByPagination(ArticlePaginationRequest articlePaginationRequest) {
		return articleService.findAllArticlesByPagination(articlePaginationRequest).build();
	}
	
	@TimerExecution
	@GetMapping("/article/find/{id}")
	public ResponseEntity<?> findArticle(@PathVariable Long id) {
		return articleService.findArticle(id).build();
	}
	
	@TimerExecution
	@PutMapping("/article/update")
	public ResponseEntity<?> updateUser(@Valid @RequestBody ArticleRequest articleRequest) {
		return articleService.updateArticle(articleRequest).build();
	}
	
	@TimerExecution
	@PostMapping("/article/create")
	public ResponseEntity<?> createArticle(@Valid @RequestBody ArticleCreateRequest articleCreateRequest) {
		return articleService.createArticle(articleCreateRequest).build();
	}
	
	@TimerExecution
	@DeleteMapping("/article/delete/{id}")
	public ResponseEntity<?> deleteArticle(@PathVariable("id") Long id) {
		return articleService.deleteArticle(id).build();
	}
}