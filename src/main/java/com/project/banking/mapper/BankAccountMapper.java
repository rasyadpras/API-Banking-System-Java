package com.project.banking.mapper;

import com.project.banking.dto.response.bankacc.*;
import com.project.banking.entity.*;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    public BankAccountResponse toBankAccountResponse(BankAccount bankAccount) {
        return BankAccountResponse.builder()
                .id(bankAccount.getId())
                .branch(toBranchResponse(bankAccount.getBranch()))
                .profile(toProfileResponse(bankAccount.getProfile()))
                .accountNumber(bankAccount.getAccountNumber())
                .bankAccountType(bankAccount.getType())
                .status(bankAccount.getStatus())
                .cards(bankAccount.getCards().stream()
                        .map(this::toCardResponse)
                        .toList())
                .createdAt(bankAccount.getCreatedAt())
                .updatedAt(bankAccount.getUpdatedAt())
                .build();
    }

    private CardResponseToBankAccount toCardResponse(Card card) {
        return CardResponseToBankAccount.builder()
                .id(card.getId())
                .cardType(card.getCardType())
                .cardNumber(card.getCardNumber())
                .principal(card.getPrincipal())
                .validThru(card.getExpiredDate())
                .status(card.getStatus())
                .build();
    }

    private BranchResponseToBankAccount toBranchResponse(Branch branch) {
        return BranchResponseToBankAccount.builder()
                .branchId(branch.getId())
                .code(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .region(branch.getRegion())
                .address(branch.getAddress())
                .build();
    }

    private ProfileResponseToBankAccount toProfileResponse(Profile profile) {
        return ProfileResponseToBankAccount.builder()
                .profileId(profile.getId())
                .fullName(profile.getFullName())
                .gender(profile.getGender())
                .birthDate(profile.getBirthDate())
                .identityType(profile.getIdentityType())
                .identityNumber(profile.getIdentityNumber())
                .address(profile.getAddress())
                .city(profile.getCity())
                .province(profile.getProvince())
                .country(profile.getCountry())
                .phoneNumber(profile.getPhoneNumber())
                .user(toUserResponse(profile.getUser()))
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    private UserResponseToBankAccount toUserResponse(User user) {
        return UserResponseToBankAccount.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}
