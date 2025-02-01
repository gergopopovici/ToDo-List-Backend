package edu.bbte.idde.pgim2289.web.servlet.DTO;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
public class StatDTO {
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer logQueries;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer logUpdates;
}
