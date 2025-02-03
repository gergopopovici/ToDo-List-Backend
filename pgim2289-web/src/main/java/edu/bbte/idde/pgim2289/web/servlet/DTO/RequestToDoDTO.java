package edu.bbte.idde.pgim2289.web.servlet.DTO;

import edu.bbte.idde.pgim2289.backend.model.ToDo;
import lombok.Getter;

import java.util.Collection;

@Getter
public class RequestToDoDTO {
    private Collection<ToDo> todo;
}
