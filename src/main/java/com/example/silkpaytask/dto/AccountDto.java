package com.example.silkpaytask.dto;

import com.example.silkpaytask.entities.Account.Transaction;
import com.example.silkpaytask.entities.UserData;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AccountDto {

    @NotNull
    private Long userId;
    private BigDecimal balance;

}
