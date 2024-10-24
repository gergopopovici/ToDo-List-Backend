package edu.bbte.idde.pgim2289.services;

import edu.bbte.idde.pgim2289.exceptions.EntityNotFoundException;
import edu.bbte.idde.pgim2289.exceptions.InvalidInputException;
import edu.bbte.idde.pgim2289.model.ToDo;

import java.text.ParseException;
import java.util.Collection;

public interface ToDoService {
    void create(String title, String description,String dueDate,String Priority) throws ParseException;
    Collection<ToDo> findAll();
    void delete(Long ID) throws EntityNotFoundException;
    void update(Long iD,String title, String description,String dueDate,String Priority) throws EntityNotFoundException, ParseException;
    Collection<ToDo>findByPriority(Integer Priority);
}
