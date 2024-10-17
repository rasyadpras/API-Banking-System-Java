package com.project.banking.controller;

import com.project.banking.dto.request.*;
import com.project.banking.dto.response.auth.LoginResponse;
import com.project.banking.dto.response.auth.RegisterResponse;
import com.project.banking.dto.response.format.SuccessResponse;
import com.project.banking.dto.response.useracc.UserResponse;
import com.project.banking.service.AuthService;
import com.project.banking.utils.constant.APIUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.AUTHENTICATION_API)
public class AuthController {
    private final AuthService authService;

    @PostMapping(
            path = APIUrl.PATH_REGISTER,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<RegisterResponse>> register(@RequestBody RegisterRequest request) {
        RegisterResponse register = authService.register(request);
        SuccessResponse<RegisterResponse> response = SuccessResponse.<RegisterResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(register)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping(
            path = APIUrl.PATH_LOGIN,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<LoginResponse>> login(@RequestBody LoginRequest request) {
        LoginResponse login = authService.login(request);
        SuccessResponse<LoginResponse> response = SuccessResponse.<LoginResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(login)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @PatchMapping(
            path = APIUrl.PATH_ADD_ROLE,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<UserResponse>> addRole(@RequestBody UpdateUserAddRoleRequest request) {
        UserResponse user = authService.addRole(request);
        SuccessResponse<UserResponse> response = SuccessResponse.<UserResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(user)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(
            path = APIUrl.PATH_FORGOT_PASS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<String>> forgotPassword(@RequestBody ForgotUserPasswordRequest request) {
        authService.forgotPassword(request);
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data("Password has been changed")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PatchMapping(
            path = APIUrl.PATH_RESET_PASS,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<String>> resetPassword(@RequestBody ResetUserPasswordRequest request) {
        authService.resetPassword(request);
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data("Password has been changed")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @PatchMapping(
            path = APIUrl.PATH_VERIFY + APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<String>> verifyAccount(@PathVariable String id) {
        authService.verify(id);
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data("Account has been verified")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @PatchMapping(
            path = APIUrl.PATH_UNLOCK + APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<String>> unlockAccount(@PathVariable String id) {
        authService.unlock(id);
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data("Account has been unlocked")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
