package edu.bbte.idde.pgim2289.spring.repository.repo;

import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
@Profile("jpa")
public interface ToDoJpaRepo extends JpaRepository<ToDo, Long> {
    Page<ToDo> findByPriority(Integer priority, Pageable pageable);

    @Override
    Page<ToDo> findAll(Pageable pageable);
}
