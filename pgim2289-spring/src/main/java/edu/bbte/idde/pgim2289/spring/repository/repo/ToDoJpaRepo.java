package edu.bbte.idde.pgim2289.spring.repository.repo;

import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.springframework.context.annotation.Profile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.Collection;
import java.util.Date;


@Repository
@Profile("jpa")
public interface ToDoJpaRepo extends JpaRepository<ToDo, Long> {
    Collection<ToDo> findByPriority(Integer priority);

    Collection<ToDo> findByUserId(Long userId);

    @Query("SELECT t FROM ToDo t WHERE "
            + "(:priority IS NULL OR t.priority = :priority) AND "
            + "(:dueDateFrom IS NULL OR t.dueDate >= :dueDateFrom) AND "
            + "(:dueDateTo IS NULL OR t.dueDate <= :dueDateTo) AND "
            + "(:dueDate IS NULL OR t.dueDate = :dueDate)")
    Collection<ToDo> findByFilters(Integer priority, Date dueDateFrom, Date dueDateTo, Date dueDate);
}
