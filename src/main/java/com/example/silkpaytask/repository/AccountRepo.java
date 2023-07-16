package com.example.silkpaytask.repository;

import com.example.silkpaytask.entities.Account.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepo extends JpaRepository<Account, Long> {
}
