package com.ajcode.springbootstudentmanagement.dto;

import java.time.LocalDateTime;

public class UserRegisterResponse {

    private Long userId;
    private String userName;
    private String email;
    private String password;
    private String roleName;
    private LocalDateTime createdAt;

    public UserRegisterResponse(Long userId, String userName, String email, String password, String roleName) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public UserRegisterResponse() {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
