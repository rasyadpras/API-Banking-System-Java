package com.project.banking.dto.response.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.dto.response.bankacc.BranchResponseToBankAccount;
import com.project.banking.utils.constant.BankAccountStatus;
import com.project.banking.utils.constant.BankAccountType;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponseToCard {
    private String bankAccountId;
    private ProfileResponseToCard profile;
    private String accountNumber;
    private BankAccountType bankAccountType;
    private BankAccountStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
