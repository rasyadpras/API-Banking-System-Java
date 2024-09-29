package com.project.banking.service;

import com.project.banking.dto.response.security.JwtClaims;
import com.project.banking.entity.User;

public interface JwtService {
    String generateToken(User user);
    boolean verifyToken(String token);
    JwtClaims claimToken(String token);
}
