package com.project.banking.dto.response.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.BankAccountStatus;
import com.project.banking.utils.constant.BankAccountType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponseToProfile {
    private String accountId;
    private BranchResponseToProfile branch;
    private String accountNumber;
    private BankAccountType bankAccountType;
    private BankAccountStatus status;
    private List<CardResponseToProfile> cards;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
