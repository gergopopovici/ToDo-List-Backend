package edu.bbte.idde.pgim2289.spring.services;

import edu.bbte.idde.pgim2289.spring.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.spring.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.spring.model.ToDo;

import java.util.Collection;

public interface ToDoService {
    void create(ToDo toDo) throws InvalidInputException;

    Collection<ToDo> findAll();

    void delete(Long id, Long userID) throws EntityNotFoundException;

    void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException;

    Collection<ToDo> findByPriority(Integer priority);

    ToDo findById(Long id);

    Collection<ToDo> findByUserId(Long userId);
}
