package com.project.banking.service.implement;

import com.project.banking.dto.request.CreateBranchRequest;
import com.project.banking.dto.request.UpdateBranchRequest;
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

        if (branchRepo.existsByBranchCode(request.getBranchCode())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Branch code already exists");
        }

        Branch branch = Branch.builder()
                .branchCode(request.getBranchCode())
                .branchName(request.getBranchName())
                .region(request.getRegion())
                .address(request.getAddress())
                .createdAt(LocalDateTime.now())
                .build();
        branchRepo.saveAndFlush(branch);
        return mapper.toBranchResponse(branch);
    }

    @Transactional(readOnly = true)
    @Override
    public List<BranchResponse> getAll(String region) {
        List<Branch> branches;
        if (region != null) {
            branches = branchRepo.findAllByRegion(region);
        } else {
            branches = branchRepo.findAll();
        }
        return branches.stream().map(mapper::toBranchResponse).toList();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BranchResponse update(UpdateBranchRequest request, String id) {
        validation.validate(request);
        Branch branch = findId(id);
        branch.setBranchName(request.getBranchName());
        branch.setAddress(request.getAddress());
        branch.setUpdatedAt(LocalDateTime.now());
        branchRepo.save(branch);
        return mapper.toBranchResponse(branch);
    }

    @Transactional(readOnly = true)
    @Override
    public Branch findId(String id) {
        return branchRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Branch not found")
        );
    }
}
