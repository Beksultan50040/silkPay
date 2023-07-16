package com.example.silkpaytask.service.impl;

import com.example.silkpaytask.dto.AccountDto;
import com.example.silkpaytask.dto.Status;
import com.example.silkpaytask.entities.Account.Account;
import com.example.silkpaytask.entities.Account.Transaction;
import com.example.silkpaytask.entities.UserData;
import com.example.silkpaytask.exceptions.ApiError;
import com.example.silkpaytask.mapper.AccountMapper;
import com.example.silkpaytask.repository.AccountRepo;
import com.example.silkpaytask.repository.UserDataRepository;
import com.example.silkpaytask.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {

    @Autowired
    AccountRepo accountRepo;
    @Autowired
    UserDataRepository userDataRepository;
    @Autowired
    TransactionServiceImpl transactionService;

    @Override
    public Account createNew(AccountDto accountDto) {

        Account account = AccountMapper.INSTANCE.accountDtoToAccount(accountDto);
        UserData userData = findUser(accountDto.getUserId());
        Optional<Account> account1 = accountRepo.findById(accountDto.getUserId());

        if (account1.isPresent()) throw new ApiError(HttpStatus.BAD_REQUEST, "Account already exists", new ArrayList<>());

        account.setUserId(userData);

        return accountRepo.save(account);
    }
    @Override
    public Account transfer(Long fromAccountId, Long toAccountId, BigDecimal amount) {

        Account fromAccount = accountRepo.findById(fromAccountId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Unknown account with id: " + fromAccountId,
                        new ArrayList<>()));

        Account toAccount = accountRepo.findById(toAccountId)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Unknown account with id: " + toAccountId,
                        new ArrayList<>()));

        if (amount.compareTo(new BigDecimal(0)) <= 0) {
            throw new IllegalArgumentException("{\"error\":\"Amount should be greater than zero\"}");
        }
        if (fromAccount.getBalance().compareTo(amount) < 0) {
            throw new ApiError(HttpStatus.BAD_REQUEST, "Not enough funds to transfer", new ArrayList<>());
        }

        BigDecimal balanceBeforeTransactionFromAccount = fromAccount.getBalance();
        BigDecimal balanceBeforeTransactionToAccount = toAccount.getBalance();

        //update accounts and add a new transaction
        BigDecimal balanceAfterTransactionFromAccount = balanceBeforeTransactionFromAccount.subtract(amount);
        fromAccount.setBalance(balanceAfterTransactionFromAccount);
        BigDecimal balanceAfterTransactionToAccount = balanceBeforeTransactionToAccount.add(amount);
        toAccount.setBalance(balanceAfterTransactionToAccount);

        //create transaction for fromAccountId
        Transaction fromAccountTransaction = new Transaction();
        fromAccountTransaction.setAccount(fromAccount);
        fromAccountTransaction.setAmount(amount);
        fromAccountTransaction.setBalanceBeforeTransaction(balanceBeforeTransactionFromAccount);
        fromAccountTransaction.setBalanceAfterTransaction(balanceAfterTransactionFromAccount);
        fromAccountTransaction.setStatus(Status.OUT.toString());
        fromAccountTransaction.setTransactionDate(LocalDateTime.now());

        //create transaction for toAccountId
        Transaction toAccountTransaction = new Transaction();
        toAccountTransaction.setAccount(toAccount);
        toAccountTransaction.setAmount(amount);
        toAccountTransaction.setBalanceBeforeTransaction(balanceBeforeTransactionToAccount);
        toAccountTransaction.setBalanceAfterTransaction(balanceAfterTransactionToAccount);
        toAccountTransaction.setStatus(Status.IN.toString());
        toAccountTransaction.setTransactionDate(LocalDateTime.now());

        fromAccount.getTransactions().add(fromAccountTransaction);
        toAccount.getTransactions().add(toAccountTransaction);

        transactionService.save(fromAccountTransaction);
        transactionService.save(toAccountTransaction);

        accountRepo.saveAndFlush(toAccount);

        return accountRepo.saveAndFlush(fromAccount);


    }

    @Override
    public BigDecimal getBalance(Long id) {

        UserData userData = findUser(id);
        return userData.getAccount().getBalance();
    }

    @Override
    public Account topUp(Long accountId, BigDecimal amount) {

        Account account = accountRepo.findById(accountId).orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Unknown account with id: " + accountId, new ArrayList<>()));
        account.setBalance(account.getBalance().add(amount));
        return accountRepo.saveAndFlush(account);
    }

    @Override
    public UserData findUser(Long id) {
        return userDataRepository.findById(id)
                .orElseThrow(() -> new ApiError(HttpStatus.NOT_FOUND, "Unknown user with id: " + id, new ArrayList<>()));
    }

}
