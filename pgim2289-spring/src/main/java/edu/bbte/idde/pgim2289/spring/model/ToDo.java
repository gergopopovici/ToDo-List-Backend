package edu.bbte.idde.pgim2289.spring.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "ToDo")
public class ToDo extends BaseEntity {
    @Getter
    @Setter
    @Column(name = "title", nullable = false)
    private String title;
    @Setter
    @Getter
    @Column(name = "description")
    private String description;
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Setter
    @Getter
    @Column(name = "priority")
    private Integer priority;

    @JoinColumn(name = "userId", nullable = false)
    @Getter
    @Setter
    private Long userId;

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
