package edu.bbte.idde.pgim2289.spring.dto;

import edu.bbte.idde.pgim2289.spring.model.ToDo;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.Date;

public class ToDoDTO {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Date date;
    @NotNull
    private Integer priority;

    public Long getid() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Date getDate() {
        return date;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public static ToDoDTO fromEntity(ToDo toDo) {
        ToDoDTO dto = new ToDoDTO();
        dto.setId(toDo.getId());
        dto.setTitle(toDo.getTitle());
        dto.setDescription(toDo.getDescription());
        dto.setDate(toDo.getDate());
        dto.setPriority(toDo.getPriority());
        return dto;
    }

    public ToDo toEntity() {
        ToDo toDo = new ToDo();
        toDo.setId(this.getid());
        toDo.setTitle(this.getTitle());
        toDo.setDescription(this.getDescription());
        toDo.setDate(this.getDate());
        toDo.setPriority(this.getPriority());
        return toDo;
    }
}
