package com.project.banking.mapper;

import com.project.banking.dto.response.bankacc.BankAccountResponse;
import com.project.banking.dto.response.bankacc.ProfileResponseToBankAccount;
import com.project.banking.dto.response.bankacc.UserResponseToBankAccount;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.Profile;
import com.project.banking.entity.User;
import org.springframework.stereotype.Component;

@Component
public class BankAccountMapper {
    private final BranchMapper branchMapper;

    private BankAccountMapper(BranchMapper branchMapper) {
        this.branchMapper = branchMapper;
    }

    public BankAccountResponse toBankAccountResponse(BankAccount bankAccount) {
        return BankAccountResponse.builder()
                .id(bankAccount.getId())
                .branch(branchMapper.toBranchResponse(bankAccount.getBranch()))
                .profile(toProfileResponse(bankAccount.getProfile()))
                .accountNumber(bankAccount.getAccountNumber())
                .status(bankAccount.getStatus())
                .createdAt(bankAccount.getCreatedAt())
                .updatedAt(bankAccount.getUpdatedAt())
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
