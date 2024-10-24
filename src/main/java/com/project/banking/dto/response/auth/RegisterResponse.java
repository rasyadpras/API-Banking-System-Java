package com.project.banking.dto.response.auth;

import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterResponse {
    private String userId;
    private String email;
    private List<String> roles;
    private ProfileResponseToRegister profile;
}
