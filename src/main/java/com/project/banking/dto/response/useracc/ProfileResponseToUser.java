package com.project.banking.dto.response.useracc;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.Gender;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileResponseToUser {
    private String id;
    private String fullName;
    private Gender gender;
}
