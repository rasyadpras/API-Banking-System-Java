package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserAddRoleRequest {
    @NotBlank(message = "Password required")
    private String role;
}
