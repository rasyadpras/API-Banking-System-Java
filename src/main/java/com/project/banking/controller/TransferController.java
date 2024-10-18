package com.project.banking.controller;

import com.project.banking.dto.request.CreateTransferRequest;
import com.project.banking.dto.response.format.SuccessResponse;
import com.project.banking.dto.response.transfer.TransferResponse;
import com.project.banking.service.TransferService;
import com.project.banking.utils.constant.APIUrl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = APIUrl.TRANSACTION_API)
public class TransferController {
    private final TransferService transferService;

    @PostMapping(
            path = APIUrl.PATH_TRANSFER,
            produces = MediaType.APPLICATION_JSON_VALUE,
            consumes = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<TransferResponse>> createTransferTransaction(@RequestBody CreateTransferRequest request) {
        TransferResponse transfer = transferService.create(request);
        SuccessResponse<TransferResponse> response = SuccessResponse.<TransferResponse>builder()
                .statusCode(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .data(transfer)
                .build();
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_TRANSFER + APIUrl.PATH_ID,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<TransferResponse>> getTransferById(@PathVariable String id) {
        TransferResponse transfer = transferService.getById(id);
        SuccessResponse<TransferResponse> response = SuccessResponse.<TransferResponse>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(transfer)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_BANK_ACCOUNT_ID + APIUrl.PATH_TRANSFER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<List<TransferResponse>>> getAllTransferByUser(@PathVariable String bankAccId) {
        List<TransferResponse> list = transferService.getAllTransferTransactionByUser(bankAccId);
        SuccessResponse<List<TransferResponse>> response = SuccessResponse.<List<TransferResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_BANK_ACCOUNT_ID + APIUrl.PATH_TRANSFER + APIUrl.PATH_SENDER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<List<TransferResponse>>> getAllBySender(@PathVariable String bankAccId) {
        List<TransferResponse> list = transferService.getAllBySender(bankAccId);
        SuccessResponse<List<TransferResponse>> response = SuccessResponse.<List<TransferResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_BANK_ACCOUNT_ID + APIUrl.PATH_TRANSFER + APIUrl.PATH_RECEIVER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<List<TransferResponse>>> getAllByReceiver(@PathVariable String bankAccId) {
        List<TransferResponse> list = transferService.getAllByReceiver(bankAccId);
        SuccessResponse<List<TransferResponse>> response = SuccessResponse.<List<TransferResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
