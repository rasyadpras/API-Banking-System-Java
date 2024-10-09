package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateBranchRequest {
    @NotBlank(message = "Branch code must be not blank")
    @Pattern(regexp = "\\d{3}", message = "Branch code must consist 3 digits of number")
    private String branchCode;

    @NotBlank(message = "Branch name must be not blank")
    private String branchName;

    @NotBlank(message = "Region must be not blank")
    private String region;

    @NotBlank(message = "City must be not blank")
    private String address;
}
