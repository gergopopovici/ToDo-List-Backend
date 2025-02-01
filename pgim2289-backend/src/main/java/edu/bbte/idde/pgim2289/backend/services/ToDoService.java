package edu.bbte.idde.pgim2289.backend.services;

import edu.bbte.idde.pgim2289.backend.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.backend.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.backend.model.ToDo;

import java.util.Collection;

public interface ToDoService {
    void create(ToDo toDo) throws InvalidInputException;

    Collection<ToDo> findAll();

    void delete(Long id) throws EntityNotFoundException;

    void update(ToDo toDo) throws EntityNotFoundException, InvalidInputException;

    Collection<ToDo> findByPriority(Integer priority);

    ToDo findById(Long id);

    public Integer getLogUpdateCount();
    public Integer getLogQueryCount();
}
