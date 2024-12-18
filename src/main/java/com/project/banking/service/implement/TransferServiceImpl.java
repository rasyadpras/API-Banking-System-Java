package com.project.banking.service.implement;

import com.project.banking.dto.request.CreateTransferRequest;
import com.project.banking.dto.response.transfer.TransferResponse;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.Profile;
import com.project.banking.entity.Transfer;
import com.project.banking.mapper.TransferMapper;
import com.project.banking.repository.TransferRepository;
import com.project.banking.service.BankAccountService;
import com.project.banking.service.TransferService;
import com.project.banking.service.UserService;
import com.project.banking.utils.component.ConverterUtil;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.BankAccountStatus;
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
public class TransferServiceImpl implements TransferService {
    private final TransferRepository transferRepo;
    private final ValidationUtil validation;
    private final TransferMapper mapper;

    private final BankAccountService bankAccountService;
    private final UserService userService;
    private final ConverterUtil converter;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransferResponse create(CreateTransferRequest request) {
        validation.validate(request);

        Profile currentUser = userService.getByContext().getProfile();
        BankAccount sourceAcc = bankAccountService.findByAccountNumber(request.getSourceAccountNumber());
        BankAccount destinationAcc = bankAccountService.findByAccountNumber(request.getDestinationAccountNumber());
        BigDecimal convertedAmount = converter.convertToBigDecimal(request.getAmount());

        if (!currentUser.getId().equals(sourceAcc.getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        if (destinationAcc.getStatus().equals(BankAccountStatus.CLOSED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination account is closed");
        }

        if (sourceAcc.getBalance().compareTo(convertedAmount) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        if (request.getSourceAccountNumber().equals(request.getDestinationAccountNumber())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Cannot transfer to the same account");
        }

        sourceAcc.setBalance(sourceAcc.getBalance().subtract(convertedAmount));
        sourceAcc.setUpdatedAt(LocalDateTime.now());
        destinationAcc.setBalance(destinationAcc.getBalance().add(convertedAmount));
        destinationAcc.setUpdatedAt(LocalDateTime.now());

        Transfer transfer = Transfer.builder()
                .sourceAccount(sourceAcc)
                .destinationAccount(destinationAcc)
                .amount(convertedAmount)
                .transactionDate(LocalDateTime.now())
                .build();
        transferRepo.saveAndFlush(transfer);
        return mapper.toTransferResponse(transfer);
    }

    @Transactional(readOnly = true)
    @Override
    public TransferResponse getById(String id) {
        Profile currentUser = userService.getByContext().getProfile();
        Transfer transfer = findId(id);

        if (!currentUser.getId().equals(transfer.getSourceAccount().getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        return mapper.toTransferResponse(transfer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransferResponse> getAllBySender(String bankAccId) {
        Profile currentUser = userService.getByContext().getProfile();
        BankAccount account = bankAccountService.findId(bankAccId);

        if (!currentUser.getId().equals(account.getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        List<Transfer> transfers = transferRepo.findAllBySenderAcc(bankAccId);
        return transfers.stream().map(mapper::toTransferResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransferResponse> getAllByReceiver(String bankAccId) {
        Profile currentUser = userService.getByContext().getProfile();
        BankAccount account = bankAccountService.findId(bankAccId);

        if (!currentUser.getId().equals(account.getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        List<Transfer> transfers = transferRepo.findAllByReceiverAcc(bankAccId);
        return transfers.stream().map(mapper::toTransferResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransferResponse> getAllTransferTransactionByUser(String bankAccId) {
        Profile currentUser = userService.getByContext().getProfile();
        BankAccount account = bankAccountService.findId(bankAccId);

        if (!currentUser.getId().equals(account.getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        List<Transfer> transfers = transferRepo.findAllByUserTransfer(bankAccId);
        return transfers.stream().map(mapper::toTransferResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public Transfer findId(String id) {
        return transferRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Transaction not found")
        );
    }
}
