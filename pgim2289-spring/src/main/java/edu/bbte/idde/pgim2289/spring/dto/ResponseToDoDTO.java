package edu.bbte.idde.pgim2289.spring.dto;

import lombok.Data;

import java.time.Instant;
import java.util.Date;

@Data
public class ResponseToDoDTO {
    private Long id;
    private String title;
    private String description;
    private Date date;
    private Integer priority;
    private Instant createdAt;
}
