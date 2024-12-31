package edu.bbte.idde.pgim2289.spring.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="Task")
public class Task extends BaseEntity{
    @Getter
    @Setter
    @Column(name="description")
    @NotBlank
    private String description;
    @Getter
    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="todo_id", nullable = false)
    @JsonIgnore
    private ToDo toDo;

    public Task() {
    }
    public Task(String description, ToDo toDo) {
        this.description = description;
        this.toDo = toDo;
    }

}
