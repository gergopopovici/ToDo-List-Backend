package edu.bbte.idde.pgim2289.spring.services;

import edu.bbte.idde.pgim2289.spring.dto.ToDoFilterDTO;
import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;

public interface ToDoService {
    void create(ToDo toDo) throws InvalidInputException;

    Collection<ToDo> findAll();

    Page<ToDo> findAll(Pageable pageable);

    void delete(Long id) throws EntityNotFoundException;

    void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException;

    Collection<ToDo> findByPriority(Integer priority);

    Page<ToDo> findByPriority(Integer priority, Pageable pageable);

    ToDo findById(Long id);

    Page<ToDo> filterToDos(ToDoFilterDTO filterDTO, Pageable pageable);
}
