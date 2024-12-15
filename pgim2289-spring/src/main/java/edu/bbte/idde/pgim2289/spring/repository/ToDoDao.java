package edu.bbte.idde.pgim2289.spring.repository;

import edu.bbte.idde.pgim2289.spring.model.ToDo;

import java.util.Collection;

public interface ToDoDao extends Dao<ToDo> {
    Collection<ToDo> findByPriority(Integer priority);
}
