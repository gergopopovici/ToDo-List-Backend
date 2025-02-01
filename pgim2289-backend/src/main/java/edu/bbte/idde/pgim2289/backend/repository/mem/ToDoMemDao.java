package edu.bbte.idde.pgim2289.backend.repository.mem;

import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.repository.ToDoDao;

import java.util.Collection;
import java.util.stream.Collectors;

public class ToDoMemDao extends MemDao<ToDo> implements ToDoDao {

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        return entities.values().stream()
                .filter(toDo -> toDo.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    @Override
    public Integer getLogQueriesNumbers() {
        return 0;
    }

    @Override
    public Integer getLogUpdatesNumbers() {
        return 0;
    }
}
