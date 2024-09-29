package com.project.banking.dto.response.transfer;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseToTransfer {
    private String profileId;
    private String fullName;
}
