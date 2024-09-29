package com.project.banking.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateBankAccountStatusRequest {
    @NotBlank(message = "Bank account id must be not blank")
    private String bankAccountId;
}
