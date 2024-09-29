package com.project.banking.mapper;

import com.project.banking.dto.response.transfer.BankAccountResponseToTransfer;
import com.project.banking.dto.response.transfer.ProfileResponseToTransfer;
import com.project.banking.dto.response.transfer.TransferResponse;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.Profile;
import com.project.banking.entity.Transfer;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class TransferMapper {
    public TransferResponse toTransferResponse(Transfer transfer) {
        return TransferResponse.builder()
                .id(transfer.getId())
                .sourceAccount(toBankAccountResponse(transfer.getSourceAccount()))
                .destinationAccount(toBankAccountResponse(transfer.getDestinationAccount()))
                .amount(transfer.getAmount())
                .transactionDate(LocalDateTime.now())
                .build();
    }

    private BankAccountResponseToTransfer toBankAccountResponse(BankAccount bankAccount) {
        return BankAccountResponseToTransfer.builder()
                .accountId(bankAccount.getId())
                .profile(toProfileResponse(bankAccount.getProfile()))
                .accountNumber(bankAccount.getAccountNumber())
                .build();
    }

    private ProfileResponseToTransfer toProfileResponse(Profile profile) {
        return ProfileResponseToTransfer.builder()
                .profileId(profile.getId())
                .fullName(profile.getFullName())
                .build();
    }
}
