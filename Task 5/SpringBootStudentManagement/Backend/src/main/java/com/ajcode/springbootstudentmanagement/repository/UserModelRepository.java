package com.ajcode.springbootstudentmanagement.repository;

import com.ajcode.springbootstudentmanagement.entity.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserModelRepository extends JpaRepository<UserModel,Long> {

    boolean existsByEmail(String email);
    Optional<UserModel> findByEmail(String email);
}
