package ca.est.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import ca.est.entity.UserCMS;
import jakarta.transaction.Transactional;

/**
 * @author Estevam Meneses
 */
public interface UserRepository extends JpaRepository<UserCMS, Long> {
	
	Optional<UserCMS> findByUsername(String username);
	
	@Transactional
    @Query(value = "SELECT * FROM user_blog u WHERE u.username = :username ", nativeQuery = true)
    Optional<UserCMS> findByUsernameNative(@Param("username") String username);

	Boolean existsByUsername(String username);
	// Boolean existsByEmail(String email);
}
