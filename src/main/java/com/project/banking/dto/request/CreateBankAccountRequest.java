package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBankAccountRequest {
    @NotBlank(message = "Branch Id required")
    private String branchId;

    @NotBlank(message = "Profile Id required")
    private String profileId;
}
