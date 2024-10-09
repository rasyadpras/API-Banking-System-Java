package com.project.banking.mapper;

import com.project.banking.dto.response.card.BankAccountResponseToCard;
import com.project.banking.dto.response.card.CardResponse;
import com.project.banking.dto.response.card.ProfileResponseToCard;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.Card;
import com.project.banking.entity.Profile;
import org.springframework.stereotype.Component;

@Component
public class CardMapper {
    public CardResponse toCardResponse (Card card) {
        return CardResponse.builder()
                .id(card.getId())
                .bankAccount(toBankAccountResponse(card.getBankAccount()))
                .cardType(card.getCardType())
                .cardNumber(card.getCardNumber())
                .principal(card.getPrincipal())
                .validThru(card.getExpiredDate())
                .cvv(card.getCvv())
                .status(card.getStatus())
                .activeDate(card.getActiveDate())
                .updatedAt(card.getUpdatedAt())
                .build();
    }

    private BankAccountResponseToCard toBankAccountResponse(BankAccount bankAccount) {
        return BankAccountResponseToCard.builder()
                .bankAccountId(bankAccount.getId())
                .profile(toProfileResponse(bankAccount.getProfile()))
                .accountNumber(bankAccount.getAccountNumber())
                .bankAccountType(bankAccount.getType())
                .status(bankAccount.getStatus())
                .createdAt(bankAccount.getCreatedAt())
                .updatedAt(bankAccount.getUpdatedAt())
                .build();
    }

    private ProfileResponseToCard toProfileResponse(Profile profile) {
        return ProfileResponseToCard.builder()
                .profileId(profile.getId())
                .fullName(profile.getFullName())
                .gender(profile.getGender())
                .birthDate(profile.getBirthDate())
                .identityType(profile.getIdentityType())
                .identityNumber(profile.getIdentityNumber())
                .build();
    }
}
