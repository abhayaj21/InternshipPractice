package com.ajcode.springbootstudentmanagement.service;

import com.ajcode.springbootstudentmanagement.entity.UserModel;
import com.ajcode.springbootstudentmanagement.repository.UserModelRepository;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserModelRepository userModelRepository;

    public CustomUserDetailsService(UserModelRepository userModelRepository) {
        this.userModelRepository = userModelRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserModel userModel = userModelRepository.findByEmail(username)
                .orElseThrow(()->new UsernameNotFoundException("user not found"));
        return buildUserDetails(userModel);
    }

    private UserDetails buildUserDetails(UserModel userModel){
        String roleName = userModel.getRole().getRoleName();
        return User.builder()
                .username(userModel.getEmail())
                .password(userModel.getPassword())
                .authorities(roleName.toUpperCase())
                .accountExpired(false)
                .disabled(false)
                .build();
    }
}
