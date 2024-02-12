package ca.est.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.est.entity.Article;

/**
 * @author Estevam Meneses
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {
	
	
	//Optional<UserBlog> findByUsername(String username);
	
	//@Transactional
    //@Query(value = "SELECT * FROM user_blog u WHERE u.username = :username ", nativeQuery = true)
    //Optional<UserBlog> findByUsernameNative(@Param("username") String username);

	//Boolean existsByUsername(String username);
}
