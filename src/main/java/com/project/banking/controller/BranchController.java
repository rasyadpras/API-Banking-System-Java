package com.project.banking.controller;

import com.project.banking.dto.request.CreateBranchRequest;
import com.project.banking.dto.request.UpdateBranchRequest;
import com.project.banking.dto.response.branch.BranchResponse;
import com.project.banking.dto.response.format.SuccessResponse;
import com.project.banking.service.BranchService;
import com.project.banking.utils.constant.APIUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.BRANCH_API)
public class BranchController {
    private final BranchService branchService;

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<BranchResponse>> createBranch(@RequestBody CreateBranchRequest request) {
        BranchResponse branch = branchService.create(request);
        SuccessResponse<BranchResponse> response = SuccessResponse.<BranchResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(branch)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<List<BranchResponse>>> getAllBranches() {
        List<BranchResponse> list = branchService.getAll();
        SuccessResponse<List<BranchResponse>> response = SuccessResponse.<List<BranchResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR')")
    @PatchMapping(
            path = APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<BranchResponse>> updateBranch(
            @RequestBody UpdateBranchRequest request,
            @PathVariable String id
    ) {
        BranchResponse branch = branchService.update(request, id);
        SuccessResponse<BranchResponse> response = SuccessResponse.<BranchResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(branch)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
