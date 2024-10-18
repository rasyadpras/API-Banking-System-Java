package com.project.banking.controller;

import com.project.banking.dto.request.CreateCashTransactionRequest;
import com.project.banking.dto.response.cashtrx.CashTransactionResponse;
import com.project.banking.dto.response.format.SuccessResponse;
import com.project.banking.service.CashTransactionService;
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
@RequestMapping(path = APIUrl.TRANSACTION_API)
public class CashTransactionController {
    private final CashTransactionService cashTransactionService;

    @PreAuthorize("hasAnyRole('OFFICER')")
    @PostMapping(
            path = APIUrl.PATH_CASH + APIUrl.PATH_DEPOSIT,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<CashTransactionResponse>> createCashDeposit(@RequestBody CreateCashTransactionRequest request) {
        CashTransactionResponse deposit = cashTransactionService.cashDeposit(request);
        SuccessResponse<CashTransactionResponse> response = SuccessResponse.<CashTransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(deposit)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PreAuthorize("hasAnyRole('OFFICER')")
    @PostMapping(
            path = APIUrl.PATH_CASH + APIUrl.PATH_WITHDRAW,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<CashTransactionResponse>> createCashWithdraw(@RequestBody CreateCashTransactionRequest request) {
        CashTransactionResponse withdraw = cashTransactionService.cashWithdrawal(request);
        SuccessResponse<CashTransactionResponse> response = SuccessResponse.<CashTransactionResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(withdraw)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_CASH + APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<CashTransactionResponse>> getTransactionById(@PathVariable String id) {
        CashTransactionResponse cash = cashTransactionService.getById(id);
        SuccessResponse<CashTransactionResponse> response = SuccessResponse.<CashTransactionResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(cash)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_BANK_ACCOUNT_ID + APIUrl.PATH_CASH,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<List<CashTransactionResponse>>> getAllTransactionByUser(
            @PathVariable String bankAccId
    ) {
        List<CashTransactionResponse> list = cashTransactionService.getAllCashTransactionByUser(bankAccId);
        SuccessResponse<List<CashTransactionResponse>> response = SuccessResponse.<List<CashTransactionResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
