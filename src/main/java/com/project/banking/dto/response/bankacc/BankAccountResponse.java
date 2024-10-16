package com.project.banking.dto.response.bankacc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.BankAccountStatus;
import com.project.banking.utils.constant.BankAccountType;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponse {
    private String id;
    private BranchResponseToBankAccount branch;
    private ProfileResponseToBankAccount profile;
    private String accountNumber;
    private BankAccountType bankAccountType;
    private BigDecimal balance;
    private BankAccountStatus bankAccountStatus;
    private List<CardResponseToBankAccount> cards;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
