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
public class CreateTransferRequest {
    @NotBlank(message = "Source account number must be not blank")
    @Pattern(regexp = "\\d{10}", message = "Invalid account number format")
    private String sourceAccountNumber;

    @NotBlank(message = "Destination account number must be not blank")
    @Pattern(regexp = "\\d{10}", message = "Invalid account number format")
    private String destinationAccountNumber;

    @NotNull(message = "Amount must be not null")
    @Min(value = 1000, message = "Transfer amount must be greater than or equal to 1000")
    private Long amount;
}
