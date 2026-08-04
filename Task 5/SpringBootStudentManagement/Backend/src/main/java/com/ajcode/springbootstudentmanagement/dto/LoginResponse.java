package com.ajcode.springbootstudentmanagement.dto;

public class LoginResponse {

    private Long userId;
    private String userName;
    private String email;
    private String token;
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public LoginResponse(Long userId, String userName, String email, String token, String refreshToken) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public LoginResponse() {
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
}
