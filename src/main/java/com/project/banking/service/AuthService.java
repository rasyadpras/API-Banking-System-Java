package com.project.banking.service;

import com.project.banking.dto.request.*;
import com.project.banking.dto.response.auth.LoginResponse;
import com.project.banking.dto.response.auth.RegisterResponse;
import com.project.banking.dto.response.useracc.UserResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);
    LoginResponse login(LoginRequest request);
    UserResponse addRole(UpdateUserAddRoleRequest request, String id);
    void forgotPassword(ForgotUserPasswordRequest request, String id);
    void resetPassword(ResetUserPasswordRequest request, String id);
    void verify(String id);
    void unlock(String id);
}
