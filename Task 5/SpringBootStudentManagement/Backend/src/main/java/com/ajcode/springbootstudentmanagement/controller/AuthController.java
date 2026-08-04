package com.ajcode.springbootstudentmanagement.controller;

import com.ajcode.springbootstudentmanagement.dto.LoginRequest;
import com.ajcode.springbootstudentmanagement.dto.LoginResponse;
import com.ajcode.springbootstudentmanagement.dto.UserRegisterRequest;
import com.ajcode.springbootstudentmanagement.dto.UserRegisterResponse;
import com.ajcode.springbootstudentmanagement.service.RefreshTokenService;
import com.ajcode.springbootstudentmanagement.service.UserModelService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final UserModelService userModelService;
    private final RefreshTokenService refreshTokenService;


    public AuthController(UserModelService userModelService, RefreshTokenService refreshTokenService) {
        this.userModelService = userModelService;
        this.refreshTokenService = refreshTokenService;
    }

    @PostMapping("/user-register")
    public ResponseEntity<UserRegisterResponse> userRegistration(@Valid @RequestBody UserRegisterRequest userRegisterRequest)
    {
            return ResponseEntity.status(HttpStatus.CREATED).body(userModelService.registerUser(userRegisterRequest));
    }

    @PostMapping("/admin-register")
    public ResponseEntity<UserRegisterResponse> adminRegistration(@Valid @RequestBody UserRegisterRequest userRegisterRequest)
    {
        return ResponseEntity.status(HttpStatus.CREATED).body(userModelService.registerAdmin(userRegisterRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> userLogin(@Valid @RequestBody LoginRequest loginRequest)
    {
            return ResponseEntity.status(HttpStatus.OK).body(userModelService.login(loginRequest));
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<Map<String,Object>> refreshToken(@RequestBody Map<String,String> token){
        return ResponseEntity.status(HttpStatus.CREATED).body(refreshTokenService.generateAccessToken(token));
    }
}
