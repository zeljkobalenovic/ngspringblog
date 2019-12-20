package zeljko.ngspringblog.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zeljko.ngspringblog.model.User;

/**
 * UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

	Optional<User> findByUserName(String username);

    
}