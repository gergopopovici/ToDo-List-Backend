package edu.bbte.idde.pgim2289.spring.model;

import jakarta.persistence.Entity;
import lombok.Data;

import java.time.Instant;

@Entity
@Data
public class StoreEntity extends BaseEntity{
    private String query;
    private Instant time;
}
