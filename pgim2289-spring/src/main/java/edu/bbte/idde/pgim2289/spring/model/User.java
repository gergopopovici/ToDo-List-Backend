package edu.bbte.idde.pgim2289.spring.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "User")
public class User extends BaseEntity {
    @Getter
    @Setter
    @Column(name = "username", nullable = false)
    private String username;
    @Getter
    @Setter
    @Column(name = "password", nullable = false)
    private String password;
    @Getter
    @Setter
    @Column(name = "email", nullable = false)
    private String email;
    @Getter
    @Setter
    @Column(name = "phone_number", nullable = false)
    private String phoneNumber;
    @Getter
    @Setter
    @Column(name = "admin", nullable = false)
    private Boolean admin;

    public User() {
        super();
    }

    public User(Long id, String username, String password, String email, String phoneNumber, Boolean admin) {
        super(id);
        this.username = username;
        this.password = password;
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.admin = admin;
    }

    @Override
    public String toString() {
        return "User{"
                + "username='" + username + '\''
                + ", password='" + password + '\''
                + ", email='" + email + '\''
                + ", number='" + phoneNumber + '\''
                + ", admin=" + admin
                + '}';
    }
}
