package edu.bbte.idde.pgim2289.spring.dto;

import lombok.Data;

@Data
public class ResponseUserDTO {
    private Long id;
    private String username;
    private String email;
    private String phoneNumber;
    private Boolean admin;
}
