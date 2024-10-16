package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ForgotUserPasswordRequest {
    @NotBlank(message = "User id must be not blank")
    private String userId;

    @NotBlank(message = "Password required")
    @Pattern(
            regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*\\W)(?!.* ).{8,}$",
            message = """
                    Password must contain one number, one lowercase letter, one uppercase letter,\s
                    one special character, no space, and it must be at least 8 characters long
                   """
    )
    private String password;
}
