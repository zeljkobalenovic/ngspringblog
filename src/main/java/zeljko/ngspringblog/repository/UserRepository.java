package zeljko.ngspringblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import zeljko.ngspringblog.model.User;

/**
 * UserRepository
 */

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    
}