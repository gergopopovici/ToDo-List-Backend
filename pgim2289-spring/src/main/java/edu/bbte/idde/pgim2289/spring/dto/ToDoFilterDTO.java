package edu.bbte.idde.pgim2289.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@AllArgsConstructor
public class ToDoFilterDTO {

    private String title;
    private Integer priority;
    private LocalDate dueDate;
    private String description;
    private int page;
    private int size;
    private String sort;
    private String direction;

    public ToDoFilterDTO(String title, Integer priority,
                         String dueDate, String description,
                         int page, int size, String sort, String direction) {
        this.title = title;
        this.priority = priority;
        this.description = description;
        if (dueDate != null && !dueDate.isEmpty()) {
            this.dueDate = LocalDate.parse(dueDate, DateTimeFormatter.ISO_LOCAL_DATE);
        }
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.direction = direction;
    }
}
