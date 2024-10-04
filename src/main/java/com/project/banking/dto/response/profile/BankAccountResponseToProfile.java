package com.project.banking.dto.response.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.BankAccountStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponseToProfile {
    private String accountId;
    private BranchResponseToProfile branch;
    private String accountNumber;
    private Long balance;
    private BankAccountStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
