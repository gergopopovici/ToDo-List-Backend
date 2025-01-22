package edu.bbte.idde.pgim2289.spring.specifications;

import edu.bbte.idde.pgim2289.spring.dto.ToDoFilterDTO;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ToDoSpecification {
    public static Specification<ToDo> filterToDo(ToDoFilterDTO filter) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (filter.getTitle() != null && !filter.getTitle().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("title"), "%" + filter.getTitle() + "%"));
            }
            if (filter.getPriority() != null) {
                predicates.add(criteriaBuilder.equal(root.get("priority"), filter.getPriority()));
            }

            if (filter.getDueDate() != null) {
                LocalDate startOfDay = filter.getDueDate().atStartOfDay().toLocalDate();
                LocalDate endOfDay = filter.getDueDate().plusDays(1).atStartOfDay().toLocalDate();
                predicates.add(criteriaBuilder.between(root.get("dueDate"), startOfDay, endOfDay));
            }


            if (filter.getDescription() != null && !filter.getDescription().isEmpty()) {
                predicates.add(criteriaBuilder.like(root.get("description"), "%" + filter.getDescription() + "%"));
            }
            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
