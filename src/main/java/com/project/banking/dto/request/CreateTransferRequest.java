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
public class CreateTransferRequest {
    @NotBlank(message = "Source account id must be not blank")
    private String sourceAccountId;

    @NotBlank(message = "Destination account id must be not blank")
    private String destinationAccountId;

    @NotNull(message = "Amount must be not null")
    @Min(value = 1000, message = "Transfer amount must be greater than or equal to 1000")
    private Long amount;
}
