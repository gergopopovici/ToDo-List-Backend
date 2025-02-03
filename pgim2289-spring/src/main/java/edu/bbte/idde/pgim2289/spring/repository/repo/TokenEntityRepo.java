package edu.bbte.idde.pgim2289.spring.repository.repo;

import edu.bbte.idde.pgim2289.spring.model.TokenEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

public interface TokenEntityRepo extends JpaRepository<TokenEntity, Long> {
    Collection<TokenEntity> findByValue(String token);
}
