package com.project.banking.dto.response.branch;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BranchResponse {
    private String branchId;
    private String code;
    private String branchName;
}
