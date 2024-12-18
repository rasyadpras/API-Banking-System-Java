package com.project.banking.service;

import com.project.banking.dto.request.CreateBankAccountRequest;
import com.project.banking.dto.response.bankacc.BankAccountResponse;
import com.project.banking.entity.BankAccount;

public interface BankAccountService {
    BankAccountResponse create(CreateBankAccountRequest request);
    BankAccountResponse getById(String id);
    void delete(String id);

    BankAccount findId(String id);
    BankAccount findByAccountNumber(String accountNumber);
}
