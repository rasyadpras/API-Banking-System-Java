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
@RequestMapping(path = APIUrl.TRANSACTION_API + APIUrl.PATH_TRANSFER)
public class TransferController {
    private final TransferService transferService;

    @PostMapping(
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
            path = APIUrl.PATH_ID,
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

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<SuccessResponse<List<TransferResponse>>> getAllTransferByUser() {
        List<TransferResponse> list = transferService.getAllTransferTransactionByUser();
        SuccessResponse<List<TransferResponse>> response = SuccessResponse.<List<TransferResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_SENDER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<List<TransferResponse>>> getAllBySender() {
        List<TransferResponse> list = transferService.getAllBySender();
        SuccessResponse<List<TransferResponse>> response = SuccessResponse.<List<TransferResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping(
            path = APIUrl.PATH_RECEIVER,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<SuccessResponse<List<TransferResponse>>> getAllByReceiver() {
        List<TransferResponse> list = transferService.getAllByReceiver();
        SuccessResponse<List<TransferResponse>> response = SuccessResponse.<List<TransferResponse>>builder()
                .statusCode(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .data(list)
                .build();
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}
