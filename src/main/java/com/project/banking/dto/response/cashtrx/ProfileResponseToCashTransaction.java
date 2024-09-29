package com.project.banking.dto.response.cashtrx;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseToCashTransaction {
    private String profileId;
    private String fullName;
}
