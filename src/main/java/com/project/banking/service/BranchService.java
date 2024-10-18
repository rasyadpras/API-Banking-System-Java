package com.project.banking.service;

import com.project.banking.dto.request.CreateBranchRequest;
import com.project.banking.dto.request.UpdateBranchRequest;
import com.project.banking.dto.response.branch.BranchResponse;
import com.project.banking.entity.Branch;

import java.util.List;

public interface BranchService {
    BranchResponse create(CreateBranchRequest request);
    List<BranchResponse> getAll(String region);
    BranchResponse update(UpdateBranchRequest request, String id);

    Branch findId(String id);
}
