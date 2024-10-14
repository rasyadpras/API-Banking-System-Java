package com.project.banking.dto.response.cashtrx;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.TransactionCashType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CashTransactionResponse {
    private String id;
    private BankAccountResponseToCashTransaction bankAccount;
    private TransactionCashType transactionType;
    private BigDecimal amount;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime transactionDate;
}
