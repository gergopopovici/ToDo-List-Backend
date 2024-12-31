package edu.bbte.idde.pgim2289.spring.dto;

import jakarta.validation.constraints.*;
import lombok.Data;

import java.util.Date;

@Data
public class RequestToDoDTO {
    @NotBlank
    private String title;
    @NotBlank
    private String description;
    @NotNull
    private Date date;
    @NotNull
    @Positive
    @Min(1)
    @Max(3)
    private Integer priority;
}
