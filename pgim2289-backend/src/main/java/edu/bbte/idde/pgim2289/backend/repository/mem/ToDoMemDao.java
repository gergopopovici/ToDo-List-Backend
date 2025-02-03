package edu.bbte.idde.pgim2289.backend.repository.mem;

import edu.bbte.idde.pgim2289.backend.model.ToDo;
import edu.bbte.idde.pgim2289.backend.repository.ToDoDao;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class ToDoMemDao extends MemDao<ToDo> implements ToDoDao {

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        return entities.values().stream()
                .filter(toDo -> toDo.getPriority().equals(priority))
                .collect(Collectors.toList());
    }

    @Override
    public Collection<ToDo> findBetweenPriority(int min, int max) {
        return entities.values().stream()
                .filter(toDo -> toDo.getPriority()>=min && toDo.getPriority()<=max)
                .collect(Collectors.toList());
    }
}
