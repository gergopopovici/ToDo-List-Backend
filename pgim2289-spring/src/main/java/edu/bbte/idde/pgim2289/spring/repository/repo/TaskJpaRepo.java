package edu.bbte.idde.pgim2289.spring.repository.repo;

import edu.bbte.idde.pgim2289.spring.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskJpaRepo extends JpaRepository<Task, Long> {
}
