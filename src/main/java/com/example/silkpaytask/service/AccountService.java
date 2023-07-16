package com.example.silkpaytask.service;

import com.example.silkpaytask.dto.AccountDto;
import com.example.silkpaytask.entities.Account.Account;
import com.example.silkpaytask.entities.Account.Transaction;
import com.example.silkpaytask.entities.UserData;

import java.math.BigDecimal;

public interface AccountService {

    Account transfer(Long fromAccount, Long toAccount, BigDecimal amount);
    Account createNew(AccountDto AccountDto);
    BigDecimal getBalance(Long id);
    Account topUp(Long accountId, BigDecimal amount);
    UserData findUser(Long id);

}
