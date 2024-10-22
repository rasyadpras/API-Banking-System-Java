package com.project.banking.dto.response.useracc;

import com.project.banking.utils.constant.Gender;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseToUser {
    private String profileId;
    private String fullName;
    private Gender gender;
}
