package com.project.banking.mapper;

import com.project.banking.dto.response.cashtrx.BankAccountResponseToCashTransaction;
import com.project.banking.dto.response.cashtrx.CashTransactionResponse;
import com.project.banking.dto.response.cashtrx.ProfileResponseToCashTransaction;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.CashTransaction;
import com.project.banking.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class CashTransactionMapper {
    public CashTransactionResponse toCashTransactionResponse(CashTransaction cashTransaction) {
        return CashTransactionResponse.builder()
                .id(cashTransaction.getId())
                .bankAccount(toBankAccountResponse(cashTransaction.getBankAccount()))
                .transactionType(cashTransaction.getTransactionType())
                .amount(cashTransaction.getAmount())
                .transactionDate(cashTransaction.getTransactionDate())
                .build();
    }

    private BankAccountResponseToCashTransaction toBankAccountResponse(BankAccount bankAccount) {
        return BankAccountResponseToCashTransaction.builder()
                .accountId(bankAccount.getId())
                .profile(toProfileResponse(bankAccount.getProfile()))
                .accountNumber(bankAccount.getAccountNumber())
                .bankAccountType(bankAccount.getType())
                .build();
    }

    private ProfileResponseToCashTransaction toProfileResponse(Profile profile) {
        return ProfileResponseToCashTransaction.builder()
                .profileId(profile.getId())
                .fullName(profile.getFullName())
                .build();
    }
}
