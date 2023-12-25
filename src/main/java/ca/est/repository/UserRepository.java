package ca.est.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.est.entity.UserBlog;
import jakarta.transaction.Transactional;

/**
 * @author Estevam Meneses
 */
public interface UserRepository extends JpaRepository<UserBlog, Long> {
	
	Optional<UserBlog> findByUsername(String username);
	
	@Transactional
    @Query(value = "SELECT * FROM user_blog u WHERE u.username = :username ", nativeQuery = true)
    Optional<UserBlog> findByUsernameNative(@Param("username") String username);

	Boolean existsByUsername(String username);
	// Boolean existsByEmail(String email);
}
