package com.devsync.entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name ="app_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true, length = 50)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "email", nullable = false, unique = true, length = 100)
    private String email;

    @Column(name = "is_manager", nullable = false)
    private Boolean isManager;

    @Column(name = "daily_tokens", nullable = false)
    private int dailyTokens = 2;

    @Column(name = "monthly_tokens", nullable = false)
    private int monthlyTokens = 1;

    public User(String username, String firstName, String lastName, String email, String password, Boolean isManager) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isManager = isManager;
    }

    public User(Long id, String username, String firstName, String lastName, String email, String password, Boolean isManager) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.isManager = isManager;
    }

    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", isManager=" + isManager +
                ", dailyTokens=" + dailyTokens +
                ", monthlyTokens=" + monthlyTokens +
                '}';
    }
}
