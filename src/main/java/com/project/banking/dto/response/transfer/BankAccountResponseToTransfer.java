package com.project.banking.dto.response.transfer;

import com.project.banking.utils.constant.BankAccountType;
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
    private BankAccountType bankAccountType;
}
