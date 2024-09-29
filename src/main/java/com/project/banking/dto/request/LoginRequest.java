package com.project.banking.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginRequest {
    @NotBlank(message = "E-mail required")
    @Email(message = "Invalid e-mail format")
    private String email;

    @NotBlank(message = "Password required")
    private String password;
}
