package edu.bbte.idde.pgim2289.spring.repository.repo;

import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.Collection;


@Repository
@Profile("jpa")
public interface ToDoJpaRepo extends JpaRepository<ToDo, Long> {
    Collection<ToDo> findByPriority(Integer priority);
    Collection<ToDo> findByCreatedAtAfter(Instant time);
}
