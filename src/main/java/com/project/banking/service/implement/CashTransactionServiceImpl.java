package com.project.banking.service.implement;

import com.project.banking.dto.request.CreateCashTransactionRequest;
import com.project.banking.dto.response.cashtrx.CashTransactionResponse;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.CashTransaction;
import com.project.banking.entity.Profile;
import com.project.banking.entity.User;
import com.project.banking.mapper.CashTransactionMapper;
import com.project.banking.repository.CashTransactionRepository;
import com.project.banking.service.BankAccountService;
import com.project.banking.service.CashTransactionService;
import com.project.banking.service.UserService;
import com.project.banking.utils.component.ConverterUtil;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.BankAccountStatus;
import com.project.banking.utils.constant.TransactionCashType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CashTransactionServiceImpl implements CashTransactionService {
    private final CashTransactionRepository cashTransactionRepo;
    private final ValidationUtil validation;
    private final CashTransactionMapper mapper;

    private final UserService userService;
    private final BankAccountService bankAccountService;
    private final ConverterUtil converter;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CashTransactionResponse cashDeposit(CreateCashTransactionRequest request) {
        validation.validate(request);
        BankAccount account = bankAccountService.findByAccountNumber(request.getAccountNumber());
        BigDecimal convertedAmount = converter.convertToBigDecimal(request.getAmount());

        if (account.getStatus().equals(BankAccountStatus.CLOSED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account is closed");
        }

        account.setBalance(account.getBalance().add(convertedAmount));
        account.setUpdatedAt(LocalDateTime.now());

        CashTransaction transaction = CashTransaction.builder()
                .bankAccount(account)
                .transactionType(TransactionCashType.DEPOSIT)
                .amount(convertedAmount)
                .transactionDate(LocalDateTime.now())
                .build();
        cashTransactionRepo.saveAndFlush(transaction);
        return mapper.toCashTransactionResponse(transaction);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public CashTransactionResponse cashWithdrawal(CreateCashTransactionRequest request) {
        validation.validate(request);
        BankAccount account = bankAccountService.findByAccountNumber(request.getAccountNumber());
        BigDecimal convertedAmount = converter.convertToBigDecimal(request.getAmount());

        if (account.getStatus().equals(BankAccountStatus.CLOSED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This account is closed");
        }

        if (account.getBalance().compareTo(convertedAmount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        account.setBalance(account.getBalance().subtract(convertedAmount));
        account.setUpdatedAt(LocalDateTime.now());

        CashTransaction transaction = CashTransaction.builder()
                .bankAccount(account)
                .transactionType(TransactionCashType.WITHDRAWAL)
                .amount(convertedAmount)
                .transactionDate(LocalDateTime.now())
                .build();
        cashTransactionRepo.saveAndFlush(transaction);
        return mapper.toCashTransactionResponse(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public CashTransactionResponse getById(String id) {
        User userByContext = userService.getByContext();
        Profile currentUser = userByContext.getProfile();
        CashTransaction transaction = findId(id);

        if (!currentUser.getId().equals(transaction.getBankAccount().getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        return mapper.toCashTransactionResponse(transaction);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CashTransactionResponse> getAllCashTransactionByUser() {
        User userByContext = userService.getByContext();
        Profile currentUser = userByContext.getProfile();
        List<BankAccount> accounts = bankAccountService.findProfile(currentUser.getId());
        List<CashTransaction> transactions = cashTransactionRepo.findAllByUserTransaction(accounts.stream()
                .map(BankAccount::getId)
                .toList());
        return transactions.stream().map(mapper::toCashTransactionResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public CashTransaction findId(String id) {
        return cashTransactionRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
        );
    }
}
