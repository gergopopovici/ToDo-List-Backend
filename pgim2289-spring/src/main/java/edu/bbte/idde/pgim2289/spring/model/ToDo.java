package edu.bbte.idde.pgim2289.spring.model;

import jakarta.persistence.*;

import java.util.Date;

@Entity
@Table(name = "ToDo")
public class ToDo extends BaseEntity {
    @Column(name = "title", nullable = false)
    private String title;
    @Column(name = "description")
    private String description;
    @Column(name = "due_date")
    @Temporal(TemporalType.DATE)
    private Date dueDate;
    @Column(name = "priority")
    private Integer priority;

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return dueDate;
    }

    public void setDate(Date date) {
        this.dueDate = date;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
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
