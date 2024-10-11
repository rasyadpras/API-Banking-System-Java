package com.project.banking.service;

import com.project.banking.dto.request.CreateCashTransactionRequest;
import com.project.banking.dto.response.cashtrx.CashTransactionResponse;
import com.project.banking.entity.CashTransaction;

import java.util.List;

public interface CashTransactionService {
    CashTransactionResponse cashDeposit(CreateCashTransactionRequest request);
    CashTransactionResponse cashWithdrawal(CreateCashTransactionRequest request);
    CashTransactionResponse getById(String id);
    List<CashTransactionResponse> getAllCashTransactionByUser();

    CashTransaction findId(String id);
}
