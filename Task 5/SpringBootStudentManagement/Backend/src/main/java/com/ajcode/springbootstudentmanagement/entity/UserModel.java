package com.ajcode.springbootstudentmanagement.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long userId;

    @Column(name = "username",length = 100,nullable = false)
    private String userName;

    @Column(name = "email",nullable = false,length = 100,unique = true)
    private String email;

    @Column(name = "password",nullable = false,length = 100)
    private String password;

    @ManyToOne(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id")
    private Role role;

    @Column(name = "create_at",nullable = false)
    private LocalDateTime createdAt;

    @PrePersist
    public void setCreatedAt()
    {
        this.createdAt = LocalDateTime.now();
    }

    public UserModel() {
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserModel(Long userId, String userName, String email, String password, Role role) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
