package com.ajcode.springbootstudentmanagement.service;

import com.ajcode.springbootstudentmanagement.dto.LoginRequest;
import com.ajcode.springbootstudentmanagement.dto.LoginResponse;
import com.ajcode.springbootstudentmanagement.dto.UserRegisterResponse;
import com.ajcode.springbootstudentmanagement.dto.UserRegisterRequest;
import com.ajcode.springbootstudentmanagement.entity.Role;
import com.ajcode.springbootstudentmanagement.entity.UserModel;
import com.ajcode.springbootstudentmanagement.mapper.UserMapper;
import com.ajcode.springbootstudentmanagement.repository.RoleRepository;
import com.ajcode.springbootstudentmanagement.repository.UserModelRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserModelService {

    private final UserModelRepository userModelRepository;
    private final UserMapper userMapper;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenService jwtTokenService;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final RefreshTokenService refreshTokenService;

    public UserModelService(UserModelRepository userModelRepository, UserMapper userMapper, AuthenticationManager authenticationManager, JwtTokenService jwtTokenService, RoleRepository roleRepository, PasswordEncoder encoder, RefreshTokenService refreshTokenService) {
        this.userModelRepository = userModelRepository;
        this.userMapper = userMapper;
        this.authenticationManager = authenticationManager;
        this.jwtTokenService = jwtTokenService;
        this.roleRepository = roleRepository;
        this.encoder = encoder;
        this.refreshTokenService = refreshTokenService;
    }

    public UserRegisterResponse registerUser(UserRegisterRequest userRegisterRequest)
    throws IllegalArgumentException{
        if(userModelRepository.existsByEmail(userRegisterRequest.getEmail()))
        {
            throw new IllegalArgumentException("User Already Exists With This Email");
        }
        Role role = roleRepository.findByRoleNameIgnoreCase("USER")
                .orElseGet(()->new Role("USER","Normal User Access Service"));

        UserModel userModel = userMapper.mapUserRegisterRequestToUserModel(userRegisterRequest,role);
        userModel.setPassword(encoder.encode(userModel.getPassword()));

        return userMapper.mapUserModelToUserRegisterResponse(userModelRepository.save(userModel));
    }

    public UserRegisterResponse registerAdmin(UserRegisterRequest userRegisterRequest)
            throws IllegalArgumentException{
        if(userModelRepository.existsByEmail(userRegisterRequest.getEmail()))
        {
            throw new IllegalArgumentException("Admin Already Exists With This Email");
        }
        Role role = roleRepository.findByRoleNameIgnoreCase("ADMIN")
                .orElseGet(()->new Role("ADMIN","Admin User Access All Site"));

        UserModel userModel = userMapper.mapUserRegisterRequestToUserModel(userRegisterRequest,role);
        userModel.setPassword(encoder.encode(userModel.getPassword()));
        return userMapper.mapUserModelToUserRegisterResponse(userModelRepository.save(userModel));
    }

    public LoginResponse login(LoginRequest loginRequest)
    throws IllegalArgumentException{
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));

        UserModel userModel = userModelRepository.findByEmail(loginRequest.getEmail())
                .orElseThrow(()-> new IllegalArgumentException("user not exists with this email"));
        
        String refreshToken = refreshTokenService.createRefreshToken(userModel).getToken();
        String token = jwtTokenService.generateToken(userModel);
        return userMapper.mapUserModelToLoginResponse(userModel,token,refreshToken);
    }

}
