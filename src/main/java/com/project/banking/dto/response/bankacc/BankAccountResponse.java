package com.project.banking.dto.response.bankacc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.dto.response.branch.BranchResponse;
import com.project.banking.utils.constant.BankAccountStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponse {
    private String id;
    private BranchResponse branch;
    private ProfileResponseToBankAccount profile;
    private String accountNumber;
    private BankAccountStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
