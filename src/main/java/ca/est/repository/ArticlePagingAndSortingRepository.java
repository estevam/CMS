package ca.est.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import ca.est.entity.Article;

/**
 * @author Estevam Meneses
 */
public interface ArticlePagingAndSortingRepository extends PagingAndSortingRepository<Article, Long> {
	Page<Article> findArticleByTitle(String title, Pageable pageable);
	Page<Article> findArticleByStatus(int status, Pageable pageable);	
}