package com.ajcode.springbootstudentmanagement.service;

import com.ajcode.springbootstudentmanagement.entity.RefreshToken;
import com.ajcode.springbootstudentmanagement.entity.UserModel;
import com.ajcode.springbootstudentmanagement.repository.RefreshTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Map;
import java.util.UUID;

@Service
public class RefreshTokenService {

    private final RefreshTokenRepository refreshTokenRepository;
    private final JwtTokenService jwtTokenService;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, JwtTokenService jwtTokenService) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.jwtTokenService = jwtTokenService;
    }

    public RefreshToken getRefreshToken(String token){
        return refreshTokenRepository.findByToken(token)
                .orElseThrow(()->new RuntimeException("Refresh Token Does Not Exists"));
    }

    public RefreshToken createRefreshToken(UserModel userModel){

        RefreshToken refreshTokenByUser = refreshTokenRepository.findByUser(userModel).orElse(null);
        if(refreshTokenByUser != null){
            refreshTokenRepository.delete(refreshTokenByUser);
        }
        RefreshToken refreshToken = new RefreshToken();
        refreshToken.setUser(userModel);
        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiration(Instant.now().plus(7, ChronoUnit.DAYS));
        return refreshTokenRepository.save(refreshToken);
    }

    public boolean isRefreshTokenExpire(RefreshToken refreshToken){
        return refreshToken.getExpiration().isBefore(Instant.now());
    }

    public Map<String,Object> generateAccessToken(Map<String,String> refreshTokenRequest){
        RefreshToken refreshToken = getRefreshToken(refreshTokenRequest.get("refreshToken"));
        if(isRefreshTokenExpire(refreshToken)){
            refreshTokenRepository.delete(refreshToken);
            throw new RuntimeException("Refresh Token Expire Login Again");
        }
        String accessToken = jwtTokenService.generateToken(refreshToken.getUser());
        return Map.of("newAccessToken",accessToken);
    }
}
