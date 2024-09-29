package com.project.banking.dto.response.cashtrx;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BankAccountResponseToCashTransaction {
    private String accountId;
    private ProfileResponseToCashTransaction profile;
    private String accountNumber;
}
