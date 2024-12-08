package edu.bbte.idde.pgim2289.spring.mapper;

import edu.bbte.idde.pgim2289.spring.dto.ToDoDTO;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {

    ToDo toEntity(ToDoDTO toDoDTO);
    ToDoDTO toDTO(ToDo toDo);
}
