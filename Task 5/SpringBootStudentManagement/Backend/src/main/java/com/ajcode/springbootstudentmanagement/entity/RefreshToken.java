package com.ajcode.springbootstudentmanagement.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
@Table(name = "refresh_tokens")
public class RefreshToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tokenId;
    @Column(name = "token",nullable = false,unique = true)
    private String token;
    @Column(name = "expiration",nullable = false)
    private Instant expiration;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserModel user;

    public RefreshToken(Long tokenId, String token, Instant expiration, UserModel user) {
        this.tokenId = tokenId;
        this.token = token;
        this.expiration = expiration;
        this.user = user;
    }

    public RefreshToken() {
    }

    public Long getTokenId() {
        return tokenId;
    }

    public void setTokenId(Long tokenId) {
        this.tokenId = tokenId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Instant getExpiration() {
        return expiration;
    }

    public void setExpiration(Instant expiration) {
        this.expiration = expiration;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }
}
