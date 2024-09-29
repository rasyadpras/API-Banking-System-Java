package com.project.banking.service;

import com.project.banking.dto.request.CreateBankAccountRequest;
import com.project.banking.dto.request.UpdateBankAccountStatusRequest;
import com.project.banking.dto.response.bankacc.BankAccountResponse;
import com.project.banking.entity.BankAccount;

import java.util.List;

public interface BankAccountService {
    BankAccountResponse create(CreateBankAccountRequest request);
    BankAccountResponse getById(String id);
    void delete(UpdateBankAccountStatusRequest request);

    BankAccount findId(String id);
    List<BankAccount> findProfile(String id);
}
