package edu.bbte.idde.pgim2289.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ToDoFilterDTO {

    private String title;
    private Integer priority;
    private LocalDate dueDate;
    private String description;
    private Integer page = 0;
    private Integer size = 10;
    private String sort = "id";
    private String direction = "ASC";

}
