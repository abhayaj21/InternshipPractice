package com.ajcode.springbootstudentmanagement.service;

import com.ajcode.springbootstudentmanagement.entity.UserModel;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;

@Service
public class JwtTokenService {
    @Value("${jwt.secretKey}")
    private String jwtSecretKey;

    @Value("${jwt.expiration}")
    private Long jwtExpiration;


    public String generateToken(UserModel userModel){
        return  Jwts.builder()
                .subject(userModel.getEmail())
                .signWith(secretKey())
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + jwtExpiration))
                .claim("name",userModel.getUserName())
                .claim("role",userModel.getRole().getRoleName())
                .compact();
    }


    public Claims getAllClaims(String token){
        return Jwts.parser()
                .verifyWith(secretKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public String getUserNameFromToken(String token){
        return getAllClaims(token).getSubject();
    }

    public String getToken(String requestHeader){
        return requestHeader.split("Bearer ")[1];
    }

    public boolean isValid(UserDetails userDetails, String token){
        String username = getUserNameFromToken(token);
        return username.equals(userDetails.getUsername()) && !isTokenExpired(token);
    }


    private boolean isTokenExpired(String token)
    {
        return getAllClaims(token).getExpiration().before(new Date());
    }

    private SecretKey secretKey(){
        return Keys.hmacShaKeyFor(jwtSecretKey.getBytes(StandardCharsets.UTF_8));
    }
}
