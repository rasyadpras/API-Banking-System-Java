package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBranchRequest {
    @NotBlank(message = "Branch id code must be not blank")
    private String branchId;

    @NotBlank(message = "Branch name must be not blank")
    private String branchName;

    @NotBlank(message = "Address must be not blank")
    private String address;
}
