package edu.bbte.idde.pgim2289.spring.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class ResponseToDoDTO {
    private Long id;
    private String title;
    private String description;
    private Date date;
    private Integer priority;
    private Long userId;
    private List<ResponseTaskDTO>tasks;
}
