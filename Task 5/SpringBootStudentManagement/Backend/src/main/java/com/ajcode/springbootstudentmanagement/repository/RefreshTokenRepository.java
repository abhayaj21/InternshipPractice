package com.ajcode.springbootstudentmanagement.repository;

import com.ajcode.springbootstudentmanagement.entity.RefreshToken;
import com.ajcode.springbootstudentmanagement.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken,Long> {
    Optional<RefreshToken> findByToken(String token);
    Optional<RefreshToken> findByUser(UserModel userModel);
}
