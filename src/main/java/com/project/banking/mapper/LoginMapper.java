package com.project.banking.mapper;

import com.project.banking.dto.response.auth.LoginResponse;
import com.project.banking.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class LoginMapper {
    public LoginResponse toLoginResponse(User user, String token) {
        return LoginResponse.builder()
                .token(token)
                .email(user.getEmail())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .build();
    }
}
