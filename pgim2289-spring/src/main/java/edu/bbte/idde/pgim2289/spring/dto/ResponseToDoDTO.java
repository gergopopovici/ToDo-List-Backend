package edu.bbte.idde.pgim2289.spring.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

@Data
public class ResponseToDoDTO {
    private Long id;
    private String title;
    private String description;
    private Date date;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer priority;
}
