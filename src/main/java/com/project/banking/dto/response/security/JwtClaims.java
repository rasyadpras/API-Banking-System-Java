package com.project.banking.dto.response.security;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtClaims {
    private String userId;
    private List<String> roles;
}
