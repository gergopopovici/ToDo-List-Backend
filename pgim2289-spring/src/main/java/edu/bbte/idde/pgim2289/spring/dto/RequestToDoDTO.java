package edu.bbte.idde.pgim2289.spring.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Date;

@Data
public class RequestToDoDTO {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Date date;
    @NotNull
    private Integer priority;
}
