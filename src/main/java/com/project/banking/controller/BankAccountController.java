package com.project.banking.controller;

import com.project.banking.dto.request.*;
import com.project.banking.dto.response.bankacc.BankAccountResponse;
import com.project.banking.dto.response.format.SuccessResponse;
import com.project.banking.service.BankAccountService;
import com.project.banking.utils.constant.APIUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.BANK_ACCOUNT_API)
public class BankAccountController {
    private final BankAccountService bankAccountService;

    @PreAuthorize("hasAnyRole('OFFICER')")
    @PostMapping(
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<BankAccountResponse>> createBankAccount(@RequestBody CreateBankAccountRequest request) {
        BankAccountResponse account = bankAccountService.create(request);
        SuccessResponse<BankAccountResponse> response = SuccessResponse.<BankAccountResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(account)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @GetMapping(
            path = APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<BankAccountResponse>> getBankAccountById(@PathVariable String id) {
        BankAccountResponse account = bankAccountService.getById(id);
        SuccessResponse<BankAccountResponse> response = SuccessResponse.<BankAccountResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(account)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'OFFICER')")
    @PatchMapping(
            path = APIUrl.PATH_ID + APIUrl.PATH_CLOSE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<String>> closeAccount(@PathVariable String id) {
        bankAccountService.delete(id);
        SuccessResponse<String> response = SuccessResponse.<String>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data("Account closed")
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
