package com.project.banking.mapper;

import com.project.banking.dto.response.profile.BankAccountResponseToProfile;
import com.project.banking.dto.response.profile.BranchResponseToProfile;
import com.project.banking.dto.response.profile.ProfileResponse;
import com.project.banking.dto.response.profile.UserResponseToProfile;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.Branch;
import com.project.banking.entity.Profile;
import com.project.banking.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class ProfileMapper {
    public ProfileResponse toProfileResponse(Profile profile) {
        return ProfileResponse.builder()
                .id(profile.getId())
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
                .bankAccounts(profile.getBankAccounts().stream()
                        .map(this::toBankAccountResponse)
                        .collect(Collectors.toList()))
                .createdAt(profile.getCreatedAt())
                .updatedAt(profile.getUpdatedAt())
                .build();
    }

    private UserResponseToProfile toUserResponse(User user) {
        return UserResponseToProfile.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .status(user.getStatus())
                .roles(user.getAuthorities().stream().map(GrantedAuthority::getAuthority).toList())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }

    private BankAccountResponseToProfile toBankAccountResponse(BankAccount bankAccount) {
        return BankAccountResponseToProfile.builder()
                .accountId(bankAccount.getId())
                .branch(toBranchResponse(bankAccount.getBranch()))
                .accountNumber(bankAccount.getAccountNumber())
                .balance(bankAccount.getBalance())
                .status(bankAccount.getStatus())
                .createdAt(bankAccount.getCreatedAt())
                .updatedAt(bankAccount.getUpdatedAt())
                .build();
    }

    private BranchResponseToProfile toBranchResponse(Branch branch) {
        return BranchResponseToProfile.builder()
                .branchId(branch.getId())
                .code(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .region(branch.getRegion())
                .build();
    }
}
