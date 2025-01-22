package edu.bbte.idde.pgim2289.spring.mapper;

import edu.bbte.idde.pgim2289.spring.dto.RequestToDoDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseToDoDTO;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ToDoMapper {
    @IterableMapping(elementTargetType = ToDo.class)
    ToDo toEntity(RequestToDoDTO requestToDoDTO);

    ResponseToDoDTO toDTO(ToDo toDo);
}
