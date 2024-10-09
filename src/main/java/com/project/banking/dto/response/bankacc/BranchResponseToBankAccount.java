package com.project.banking.dto.response.bankacc;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponseToBankAccount {
    private String branchId;
    private String code;
    private String branchName;
    private String region;
    private String address;
}
