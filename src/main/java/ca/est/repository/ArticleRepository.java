package ca.est.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ca.est.entity.Article;

/**
 * @author Estevam Meneses
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
}
