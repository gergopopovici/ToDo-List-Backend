package edu.bbte.idde.pgim2289.spring.mapper;

import edu.bbte.idde.pgim2289.spring.dto.RequestTaskDTO;
import edu.bbte.idde.pgim2289.spring.dto.ResponseTaskDTO;
import edu.bbte.idde.pgim2289.spring.model.Task;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TaskMapper {
    @IterableMapping(elementTargetType = Task.class)
    Task toEntity(RequestTaskDTO requestTaskDTO);
    ResponseTaskDTO toTask(Task task);

}
