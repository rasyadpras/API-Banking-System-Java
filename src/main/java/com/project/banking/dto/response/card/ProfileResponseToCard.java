package com.project.banking.dto.response.card;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.dto.response.bankacc.UserResponseToBankAccount;
import com.project.banking.utils.constant.Gender;
import com.project.banking.utils.constant.IdentityType;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseToCard {
    private String profileId;
    private String fullName;
    private Gender gender;
    private LocalDate birthDate;
    private IdentityType identityType;
    private String identityNumber;
}
