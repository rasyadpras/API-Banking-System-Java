package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBankAccountRequest {
    @NotBlank(message = "Branch id required")
    private String branchId;

    @NotBlank(message = "Profile id required")
    private String profileId;

    @NotBlank(message = "Bank account type must be not blank")
    private String type;
}
