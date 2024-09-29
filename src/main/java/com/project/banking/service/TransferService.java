package com.project.banking.service;

import com.project.banking.dto.request.CreateTransferRequest;
import com.project.banking.dto.response.transfer.TransferResponse;
import com.project.banking.entity.Transfer;

import java.util.List;

public interface TransferService {
    TransferResponse create(CreateTransferRequest request);
    TransferResponse getById(String id);
    List<TransferResponse> getAllBySender();
    List<TransferResponse> getAllByReceiver();
    List<TransferResponse> getAllTransferTransactionByUser();

    Transfer findId(String id);
}
