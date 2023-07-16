package com.example.silkpaytask.controllers;

import com.example.silkpaytask.dto.AccountDto;
import com.example.silkpaytask.entities.Account.Account;
import com.example.silkpaytask.service.AccountService;
import com.example.silkpaytask.service.impl.AccountServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl accountService;

    @PostMapping()
    @Operation(summary = "Secured Endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Account> createAccount(@RequestBody @Valid AccountDto accountDto){

        return ResponseEntity.ok(accountService.createNew(accountDto));
    }

    @PostMapping("/transfer")
    @Operation(summary = "Secured Endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Account> makeTransaction(@RequestParam Long fromAccountId, @RequestParam Long toAccountId,
                                   @RequestParam BigDecimal amount){

        return ResponseEntity.ok(accountService.transfer(fromAccountId, toAccountId, amount));
    }

    @PutMapping()
    @Operation(summary = "Secured Endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<Account> topUp(@RequestParam Long accountId, @RequestParam BigDecimal amount){

        return ResponseEntity.ok(accountService.topUp(accountId, amount));
    }

    @GetMapping("/balance/{accountId}")
    @Operation(summary = "Secured Endpoint", security = @SecurityRequirement(name = "bearerAuth"))
    public ResponseEntity<BigDecimal> getBalance(@PathVariable Long accountId){

        return ResponseEntity.ok(accountService.getBalance(accountId));
    }
}
