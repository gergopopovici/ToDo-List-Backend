package edu.bbte.idde.pgim2289.spring.repository;

import edu.bbte.idde.pgim2289.spring.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpa extends JpaRepository<User, Long> {
    User findByUsername(String username);
}
