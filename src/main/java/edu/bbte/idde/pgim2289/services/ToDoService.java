package edu.bbte.idde.pgim2289.services;

import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.model.ToDo;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void create(ToDo toDo) throws InvalidInputException;
    Collection<ToDo> findAll();
    void delete(Long ID) throws EntityNotFoundException;
    void update(ToDo toDo ) throws EntityNotFoundException, InvalidInputException;
    Collection<ToDo>findByPriority(Integer Priority);
}
