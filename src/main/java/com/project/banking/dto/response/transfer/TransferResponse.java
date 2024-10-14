package com.project.banking.dto.response.transfer;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TransferResponse {
    private String id;
    private BankAccountResponseToTransfer sourceAccount;
    private BankAccountResponseToTransfer destinationAccount;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionDate;
}
