package edu.bbte.idde.pgim2289.services;

import edu.bbte.idde.pgim2289.model.ToDo;

import java.util.Collection;

public interface ToDoService {
    void create(String title, String description,String dueDate,String Priority);
    Collection<ToDo> findAll();
    void delete(Long ID);
    void update(Long iD,String title, String description,String dueDate,String Priority);
    Collection<ToDo>findByPriority(Integer Priority);
}
