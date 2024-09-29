package com.project.banking.mapper;

import com.project.banking.dto.response.useracc.ProfileResponseToUser;
import com.project.banking.dto.response.useracc.UserResponse;
import com.project.banking.entity.Profile;
import com.project.banking.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {
    public UserResponse toUserResponse(User user) {
        return UserResponse.builder()
                .id(user.getId())
                .email(user.getEmail())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .status(user.getStatus())
                .profile(toProfileResponse(user.getProfile()))
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private ProfileResponseToUser toProfileResponse(Profile profile) {
        return ProfileResponseToUser.builder()
                .id(profile.getId())
                .fullName(profile.getFullName())
                .gender(profile.getGender())
                .build();
    }
}
