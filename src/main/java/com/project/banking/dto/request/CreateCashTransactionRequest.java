package com.project.banking.dto.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateCashTransactionRequest {
    @NotBlank(message = "Bank account number must be not blank")
    @Pattern(regexp = "\\d{10}", message = "Invalid account number format")
    private String accountNumber;

    @NotNull(message = "Amount must be not null")
    @Min(value = 50000, message = "Amount must be greater than or equal to 50000")
    private Long amount;
}
