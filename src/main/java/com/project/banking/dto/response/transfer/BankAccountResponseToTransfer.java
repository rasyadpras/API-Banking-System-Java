package com.project.banking.dto.response.transfer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponseToTransfer {
    private String accountId;
    private ProfileResponseToTransfer profile;
    private String accountNumber;
}
