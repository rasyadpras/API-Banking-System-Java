package com.project.banking.dto.response.profile;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponseToProfile {
    private String branchId;
    private String code;
    private String branchName;
    private String region;
    private String address;
}
