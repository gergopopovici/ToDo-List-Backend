package edu.bbte.idde.pgim2289.backend.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

public abstract class BaseEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    protected Long id;
    @Getter
    @Setter
    protected Timestamp lastUpdatedAt;

    public BaseEntity() {
    }

    public BaseEntity(Long id, Timestamp lastUpdatedAt) {
        this.id = id;
        this.lastUpdatedAt = lastUpdatedAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        BaseEntity that = (BaseEntity) other;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}