package com.project.banking.dto.response.bankacc;

import com.fasterxml.jackson.annotation.JsonFormat;
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
public class ProfileResponseToBankAccount {
    private String profileId;
    private String fullName;
    private Gender gender;
    private LocalDate birthDate;
    private IdentityType identityType;
    private String identityNumber;
    private String address;
    private String city;
    private String province;
    private String country;
    private String phoneNumber;
    private UserResponseToBankAccount user;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
