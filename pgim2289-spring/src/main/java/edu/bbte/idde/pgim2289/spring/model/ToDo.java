package edu.bbte.idde.pgim2289.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@Entity
@Table(name = "ToDo")
public class ToDo extends BaseEntity {
    @Getter
    @Setter
    @Column(name = "Title")
    private String title;
    @Setter
    @Getter
    @Column(name = "Description")
    private String description;
    @Column(name = "DueDate")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Setter
    @Getter
    @Column(name = "Priority")
    private Integer priority;
    @Getter
    @OneToMany(mappedBy = "toDo", cascade = CascadeType.ALL, orphanRemoval = true)
    @Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
    private final List<Task> tasks = new ArrayList<>();

    public void addTask(Task task) {
        tasks.add(task);
        task.setToDo(this);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
        task.setToDo(null);
    }

    public ToDo() {
        super();
    }

    public ToDo(Long id, String title, String description, Date dueDate, Integer priority) {
        super(id);
        this.dueDate = dueDate;
        this.title = title;
        this.description = description;
        this.priority = priority;
    }

    public Date getDate() {
        return dueDate;
    }

    public void setDate(Date date) {
        this.dueDate = date;
    }

    @Override
    public String toString() {
        return "ToDo{"
                + "title='" + title + '\''
                + ", description='" + description + '\''
                + ", date=" + dueDate
                + ", priority=" + priority
                + ", id=" + id
                + '}';
    }
}
