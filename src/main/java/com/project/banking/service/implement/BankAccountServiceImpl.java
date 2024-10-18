package com.project.banking.service.implement;

import com.project.banking.dto.request.CreateBankAccountRequest;
import com.project.banking.dto.response.bankacc.BankAccountResponse;
import com.project.banking.entity.BankAccount;
import com.project.banking.mapper.BankAccountMapper;
import com.project.banking.repository.BankAccountRepository;
import com.project.banking.service.BankAccountService;
import com.project.banking.service.BranchService;
import com.project.banking.service.ProfileService;
import com.project.banking.utils.component.ConverterUtil;
import com.project.banking.utils.component.ValidationUtil;
import com.project.banking.utils.constant.BankAccountStatus;
import com.project.banking.utils.constant.BankAccountType;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

@Service
@RequiredArgsConstructor
public class BankAccountServiceImpl implements BankAccountService {
    private final BankAccountRepository bankAccountRepo;
    private final ValidationUtil validation;
    private final BankAccountMapper mapper;

    private final BranchService branchService;
    private final ProfileService profileService;
    private final ConverterUtil converter;

    private final AtomicInteger sequence = new AtomicInteger(1);

    private String generateAccountNumber(String branchCode) {
        LocalDate currentDate = LocalDate.now();
        String monthYear = currentDate.format(DateTimeFormatter.ofPattern("MMyy"));
        int hashed = Math.abs(monthYear.hashCode() % 10000);
        String hashedMonthYear = String.format("%04d", hashed);

        int sequenceNumber = sequence.getAndIncrement() % 1000;
        String sequenceNumberString = String.format("%03d", sequenceNumber);

        return branchCode + hashedMonthYear + sequenceNumberString;
    }

    private BankAccountType inputType(String type) {
        return switch (type.toLowerCase()) {
            case "regular" -> BankAccountType.REGULAR_SAVINGS;
            case "business" -> BankAccountType.BUSINESS_SAVINGS;
            case "student" -> BankAccountType.STUDENT_SAVINGS;
            case "plan" -> BankAccountType.SAVINGS_PLAN;
            case "other" -> BankAccountType.OTHER_SAVINGS;
            default -> throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
                    "Bank account type must be 'regular', 'business', 'student', 'plan', or 'other'"
            );
        };
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public BankAccountResponse create(CreateBankAccountRequest request) {
        validation.validate(request);

        String accNumber = generateAccountNumber(branchService.findId(request.getBranchId()).getBranchCode());

        BankAccount account = BankAccount.builder()
                .branch(branchService.findId(request.getBranchId()))
                .profile(profileService.findId(request.getProfileId()))
                .accountNumber(accNumber)
                .type(inputType(request.getType()))
                .balance(converter.convertToBigDecimal(0L))
                .status(BankAccountStatus.ACTIVE)
                .createdAt(LocalDateTime.now())
                .build();
        bankAccountRepo.saveAndFlush(account);

        return mapper.toBankAccountResponse(account);
    }

    @Transactional(readOnly = true)
    @Override
    public BankAccountResponse getById(String id) {
        BankAccount account = findId(id);
        return mapper.toBankAccountResponse(account);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(String id) {
        BankAccount account = findId(id);
        if (account.getStatus().equals(BankAccountStatus.CLOSED)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Bank account already closed");
        }
        account.setStatus(BankAccountStatus.CLOSED);
        account.setUpdatedAt(LocalDateTime.now());
        bankAccountRepo.save(account);
    }

    @Transactional(readOnly = true)
    @Override
    public BankAccount findId(String id) {
        return bankAccountRepo.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank account not found")
        );
    }

    @Override
    public BankAccount findByAccountNumber(String accountNumber) {
        return bankAccountRepo.findByAccountNumber(accountNumber).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Bank account number not found")
        );
    }
}
