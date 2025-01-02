package edu.bbte.idde.pgim2289.spring.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RequestTaskDTO {
    @NotBlank
    private String description;
}
