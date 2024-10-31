package edu.bbte.idde.pgim2289.repository.jdbc;

import edu.bbte.idde.pgim2289.model.ToDo;
import edu.bbte.idde.pgim2289.repository.ToDoDao;

import java.util.Collection;

public class ToDoJdbcDao extends JdbcDao<ToDo> implements ToDoDao {
    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        return null;
    }
}
