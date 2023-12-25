package ca.est.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ca.est.entity.UserRole;
/**
 * @author Estevam Meneses
 */
public interface UserRoleRespository extends JpaRepository<UserRole, Long> {

}
