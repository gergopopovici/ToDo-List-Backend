package edu.bbte.idde.pgim2289.spring.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class ErrorDTO {
    private String message;
    private String path;
    private LocalDateTime timestamp;
    private int status;
}
