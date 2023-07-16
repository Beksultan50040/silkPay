package com.example.silkpaytask.service.impl;

import com.example.silkpaytask.entities.Account.Transaction;
import com.example.silkpaytask.repository.TransactionRepo;
import com.example.silkpaytask.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TransactionRepo transactionRepo;

    @Override
    public Transaction save(Transaction transaction) {
        return transactionRepo.save(transaction);
    }
}
