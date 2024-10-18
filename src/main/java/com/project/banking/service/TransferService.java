package com.project.banking.service;

import com.project.banking.dto.request.CreateTransferRequest;
import com.project.banking.dto.response.transfer.TransferResponse;
import com.project.banking.entity.Transfer;

import java.util.List;

public interface TransferService {
    TransferResponse create(CreateTransferRequest request);
    TransferResponse getById(String id);
    List<TransferResponse> getAllBySender(String bankAccId);
    List<TransferResponse> getAllByReceiver(String bankAccId);
    List<TransferResponse> getAllTransferTransactionByUser(String bankAccId);

    Transfer findId(String id);
}
