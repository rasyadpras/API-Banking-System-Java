package com.project.banking.service;

import com.project.banking.dto.request.*;
import com.project.banking.dto.response.auth.LoginResponse;
import com.project.banking.dto.response.auth.RegisterResponse;
import com.project.banking.dto.response.useracc.UserResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    UserResponse addRole(UpdateUserAddRoleRequest request);
    void updatePassword(UpdateUserPasswordRequest request);
    void verify(UpdateUserStatusRequest request);
    void unlock(UpdateUserStatusRequest request);
}
