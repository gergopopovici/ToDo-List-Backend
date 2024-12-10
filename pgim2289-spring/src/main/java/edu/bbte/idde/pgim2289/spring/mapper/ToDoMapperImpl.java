package edu.bbte.idde.pgim2289.spring.mapper;

import edu.bbte.idde.pgim2289.spring.dto.RequestToDoDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseToDoDTO;
import edu.bbte.idde.pgim2289.spring.model.ToDo;
import org.springframework.stereotype.Component;

@Component
public class ToDoMapperImpl implements ToDoMapper {

    @Override
    public ToDo toEntity(RequestToDoDTO requestToDoDTO) {
        if (requestToDoDTO == null) {
            return null;
        }
        ToDo toDo = new ToDo();
        toDo.setId(requestToDoDTO.getId());
        toDo.setTitle(requestToDoDTO.getTitle());
        toDo.setDescription(requestToDoDTO.getDescription());
        toDo.setDate(requestToDoDTO.getDate());
        toDo.setPriority(requestToDoDTO.getPriority());
        return toDo;
    }

    @Override
    public ResponseToDoDTO toDTO(ToDo toDo) {
        if (toDo == null) {
            return null;
        }
        ResponseToDoDTO responseToDoDTO = new ResponseToDoDTO();
        responseToDoDTO.setId(toDo.getId());
        responseToDoDTO.setTitle(toDo.getTitle());
        responseToDoDTO.setDescription(toDo.getDescription());
        responseToDoDTO.setDate(toDo.getDate());
        responseToDoDTO.setPriority(toDo.getPriority());
        return responseToDoDTO;
    }
}

