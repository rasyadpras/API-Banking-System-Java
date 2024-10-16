package com.project.banking.dto.response.profile;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.project.banking.utils.constant.AccountUserStatus;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponseToProfile {
    private String userId;
    private String email;
    private AccountUserStatus accountUserStatus;
    private List<String> roles;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
}
