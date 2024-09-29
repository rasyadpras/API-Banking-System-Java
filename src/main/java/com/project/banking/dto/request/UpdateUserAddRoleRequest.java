package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserAddRoleRequest {
    @NotBlank(message = "User id must be not blank")
    private String userId;

    @NotBlank(message = "Password required")
    private String role;
}
