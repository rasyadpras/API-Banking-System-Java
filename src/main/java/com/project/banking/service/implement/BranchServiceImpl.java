package com.project.banking.service.implement;

import com.project.banking.dto.request.CreateBranchRequest;
import com.project.banking.dto.response.branch.BranchResponse;
import com.project.banking.entity.Branch;
import com.project.banking.mapper.BranchMapper;
import com.project.banking.repository.BranchRepository;
import com.project.banking.service.BranchService;
import com.project.banking.utils.component.ValidationUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BranchServiceImpl implements BranchService {
    private final BranchRepository branchRepo;
    private final ValidationUtil validation;
    private final BranchMapper mapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BranchResponse create(CreateBranchRequest request) {
        validation.validate(request);
        Branch branch = Branch.builder()
                .branchCode(request.getBranchCode())
                .branchName(request.getBranchName())
                .createdAt(LocalDateTime.now())
                .build();
        branchRepo.saveAndFlush(branch);
        return mapper.toBranchResponse(branch);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BranchResponse> getAll() {
        List<Branch> branchList = branchRepo.findAll();
        return branchList.stream().map(mapper::toBranchResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Branch findId(String id) {
        return branchRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found")
        );
    }
}
