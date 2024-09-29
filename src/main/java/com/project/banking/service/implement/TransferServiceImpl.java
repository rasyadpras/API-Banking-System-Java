package com.project.banking.service.implement;

import com.project.banking.dto.request.CreateTransferRequest;
import com.project.banking.dto.response.transfer.TransferResponse;
import com.project.banking.entity.BankAccount;
import com.project.banking.entity.Profile;
import com.project.banking.entity.Transfer;
import com.project.banking.entity.User;
import com.project.banking.mapper.TransferMapper;
import com.project.banking.repository.TransferRepository;
import com.project.banking.service.BankAccountService;
import com.project.banking.service.TransferService;
import com.project.banking.service.UserService;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.BankAccountStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

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

    @Transactional(rollbackFor = Exception.class)
    @Override
    public TransferResponse create(CreateTransferRequest request) {
        validation.validate(request);

        User userByContext = userService.getByContext();
        Profile currentUser = userByContext.getProfile();
        BankAccount sourceAcc = bankAccountService.findId(request.getSourceAccountId());
        BankAccount destinationAcc = bankAccountService.findId(request.getDestinationAccountId());

        if (!currentUser.getId().equals(sourceAcc.getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        if (destinationAcc.getStatus().equals(BankAccountStatus.CLOSED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Destination account is closed");
        }

        if (sourceAcc.getBalance() < request.getAmount()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Insufficient balance");
        }

        sourceAcc.setBalance(sourceAcc.getBalance() - request.getAmount());
        sourceAcc.setUpdatedAt(LocalDateTime.now());
        destinationAcc.setBalance(destinationAcc.getBalance() + request.getAmount());
        destinationAcc.setUpdatedAt(LocalDateTime.now());

        Transfer transfer = Transfer.builder()
                .sourceAccount(sourceAcc)
                .destinationAccount(destinationAcc)
                .amount(request.getAmount())
                .build();
        transferRepo.saveAndFlush(transfer);
        return mapper.toTransferResponse(transfer);
    }

    @Transactional(readOnly = true)
    @Override
    public TransferResponse getById(String id) {
        User userByContext = userService.getByContext();
        Profile currentUser = userByContext.getProfile();
        Transfer transfer = findId(id);

        if (!currentUser.getId().equals(transfer.getSourceAccount().getProfile().getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden to access this transaction");
        }

        return mapper.toTransferResponse(transfer);
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransferResponse> getAllBySender() {
        User userByContext = userService.getByContext();
        Profile currentUser = userByContext.getProfile();
        List<BankAccount> accounts = bankAccountService.findProfile(currentUser.getId());
        List<Transfer> transfers = transferRepo.findAllBySenderAcc(accounts.stream().map(BankAccount::getId).toList());
        return transfers.stream().map(mapper::toTransferResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransferResponse> getAllByReceiver() {
        User userByContext = userService.getByContext();
        Profile currentUser = userByContext.getProfile();
        List<BankAccount> accounts = bankAccountService.findProfile(currentUser.getId());
        List<Transfer> transfers = transferRepo.findAllByReceiverAcc(accounts.stream().map(BankAccount::getId).toList());
        return transfers.stream().map(mapper::toTransferResponse).toList();
    }

    @Transactional(readOnly = true)
    @Override
    public List<TransferResponse> getAllTransferTransactionByUser() {
        User userByContext = userService.getByContext();
        Profile currentUser = userByContext.getProfile();
        List<BankAccount> accounts = bankAccountService.findProfile(currentUser.getId());
        List<Transfer> transfers = transferRepo.findAllByUserTransfer(accounts.stream().map(BankAccount::getId).toList());
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
