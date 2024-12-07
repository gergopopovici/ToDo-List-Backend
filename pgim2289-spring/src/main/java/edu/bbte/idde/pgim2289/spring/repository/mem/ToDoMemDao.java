package edu.bbte.idde.pgim2289.spring.repository.mem;

import edu.bbte.idde.pgim2289.spring.model.ToDo;
import edu.bbte.idde.pgim2289.spring.repository.ToDoDao;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.stream.Collectors;
@Repository
public class ToDoMemDao extends MemDao<ToDo> implements ToDoDao {

    @Override
    public Collection<ToDo> findByPriority(Integer priority) {
        return entities.values().stream()
                .filter(toDo -> toDo.getPriority().equals(priority))
                .collect(Collectors.toList());
    }
}
