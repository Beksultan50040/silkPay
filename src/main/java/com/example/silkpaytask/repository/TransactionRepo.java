package com.example.silkpaytask.repository;

import com.example.silkpaytask.entities.Account.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepo extends JpaRepository<Transaction, Long> {
}
