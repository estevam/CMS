package ca.est.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.graphql.data.GraphQlRepository;

import ca.est.entity.Article;

/**
 * @author Estevam Meneses
 */
@GraphQlRepository
public interface ArticleGraphQlRepository extends JpaRepository<Article, Long> {
}
