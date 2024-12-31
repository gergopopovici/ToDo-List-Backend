package edu.bbte.idde.pgim2289.spring.model;

import lombok.Getter;
import lombok.Setter;

public class User extends BaseEntity {
    @Getter
    @Setter
    private String username;
    @Getter
    @Setter
    private String password;
    @Getter
    @Setter
    private String email;
    @Getter
    @Setter
    private String number;
    @Getter
    @Setter
    private Boolean admin;

    public User() {
        super();
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", number='" + number + '\'' +
                ", admin=" + admin +
                '}';
    }
}
