package com.project.banking.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateCashTransactionRequest {
    @NotBlank(message = "Bank account id must be not blank")
    private String bankAccountId;

    @NotNull(message = "Amount must be not null")
    @Min(value = 0, message = "Amount must be greater than or equal to 0")
    private Long amount;
}
