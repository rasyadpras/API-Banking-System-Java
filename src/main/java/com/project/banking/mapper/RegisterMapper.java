package com.project.banking.mapper;

import com.project.banking.dto.response.auth.ProfileResponseToRegister;
import com.project.banking.dto.response.auth.RegisterResponse;
import com.project.banking.entity.Profile;
import com.project.banking.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class RegisterMapper {
    public RegisterResponse toRegisterResponse(User user) {
        return RegisterResponse.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .profile(toProfileResponse(user.getProfile()))
                .build();
    }

    private ProfileResponseToRegister toProfileResponse(Profile profile) {
        return ProfileResponseToRegister.builder()
                .profileId(profile.getId())
                .fullName(profile.getFullName())
                .gender(profile.getGender())
                .birthDate(profile.getBirthDate())
                .identityType(profile.getIdentityType())
                .identityNumber(profile.getIdentityNumber())
                .address(profile.getAddress())
                .city(profile.getCity())
                .province(profile.getProvince())
                .country(profile.getCountry())
                .phoneNumber(profile.getPhoneNumber())
                .build();
    }
}
