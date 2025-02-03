package edu.bbte.idde.pgim2289.spring.model;

import lombok.Data;

@Data
public class TokenEntity extends BaseEntity {
    private String value;
    private String entityName;
    private String operations;
}
