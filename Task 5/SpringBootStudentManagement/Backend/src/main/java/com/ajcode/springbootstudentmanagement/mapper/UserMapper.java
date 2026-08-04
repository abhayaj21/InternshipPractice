package com.ajcode.springbootstudentmanagement.mapper;

import com.ajcode.springbootstudentmanagement.dto.LoginRequest;
import com.ajcode.springbootstudentmanagement.dto.LoginResponse;
import com.ajcode.springbootstudentmanagement.dto.UserRegisterRequest;
import com.ajcode.springbootstudentmanagement.dto.UserRegisterResponse;
import com.ajcode.springbootstudentmanagement.entity.Role;
import com.ajcode.springbootstudentmanagement.entity.UserModel;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public UserModel mapUserRegisterRequestToUserModel(UserRegisterRequest request, Role role)
    {
        UserModel userModel = new UserModel();
        userModel.setUserName(request.getUserName());
        userModel.setEmail(request.getEmail());
        userModel.setPassword(request.getPassword());
        userModel.setRole(role);
        return userModel;
    }

    public UserRegisterResponse mapUserModelToUserRegisterResponse(UserModel userModel)
    {
        UserRegisterResponse userRegisterResponse = new UserRegisterResponse();
        userRegisterResponse.setUserId(userModel.getUserId());
        userRegisterResponse.setUserName(userModel.getUserName());
        userRegisterResponse.setPassword(userModel.getPassword());
        userRegisterResponse.setEmail(userModel.getEmail());
        userRegisterResponse.setRoleName(userModel.getRole().getRoleName());
        userRegisterResponse.setCreatedAt(userModel.getCreatedAt());
        return userRegisterResponse;
    }

    public LoginResponse mapUserModelToLoginResponse(UserModel userModel,String token,String refreshToken)
    {
        LoginResponse loginResponse = new LoginResponse();
        loginResponse.setUserId(userModel.getUserId());
        loginResponse.setEmail(userModel.getEmail());
        loginResponse.setUserName(userModel.getUserName());
        loginResponse.setToken(token);
        loginResponse.setRefreshToken(refreshToken);
        return loginResponse;
    }

}
