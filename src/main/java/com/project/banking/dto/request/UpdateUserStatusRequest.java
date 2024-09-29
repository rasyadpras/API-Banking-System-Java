package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateUserStatusRequest {
    @NotBlank(message = "User id must be not blank")
    private String userId;
}
