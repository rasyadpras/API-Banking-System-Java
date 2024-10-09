package com.project.banking.mapper;

import com.project.banking.dto.response.branch.BranchResponse;
import com.project.banking.entity.Branch;
import org.springframework.stereotype.Component;

@Component
public class BranchMapper {
    public BranchResponse toBranchResponse(Branch branch) {
        return BranchResponse.builder()
                .branchId(branch.getId())
                .code(branch.getBranchCode())
                .branchName(branch.getBranchName())
                .region(branch.getRegion())
                .address(branch.getAddress())
                .createdAt(branch.getCreatedAt())
                .updatedAt(branch.getUpdatedAt())
                .build();
    }
}
