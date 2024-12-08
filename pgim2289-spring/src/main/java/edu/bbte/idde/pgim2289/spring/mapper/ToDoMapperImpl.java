package edu.bbte.idde.pgim2289.spring.mapper;

import edu.bbte.idde.pgim2289.spring.dto.ToDoDTO;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.springframework.stereotype.Component;

@Component
public class ToDoMapperImpl implements ToDoMapper {

    @Override
    public ToDo toEntity(ToDoDTO toDoDTO) {
        if (toDoDTO == null) {
            return null;
        }
        ToDo toDo = new ToDo();
        toDo.setId(toDoDTO.getid());
        toDo.setTitle(toDoDTO.getTitle());
        toDo.setDescription(toDoDTO.getDescription());
        return toDo;
    }

    @Override
    public ToDoDTO toDTO(ToDo toDo) {
        if (toDo == null) {
            return null;
        }
        ToDoDTO toDoDTO = new ToDoDTO();
        toDoDTO.setId(toDo.getId());
        toDoDTO.setTitle(toDo.getTitle());
        toDoDTO.setDescription(toDo.getDescription());
        return toDoDTO;
    }
}

