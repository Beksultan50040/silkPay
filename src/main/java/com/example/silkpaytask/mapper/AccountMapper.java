package com.example.silkpaytask.mapper;

import com.example.silkpaytask.dto.AccountDto;
import com.example.silkpaytask.entities.Account.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import java.time.LocalDateTime;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    AccountMapper INSTANCE = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "accountCreated", expression = "java(java.time.LocalDateTime.now())")
    @Mapping(target = "userId", ignore = true)
    Account accountDtoToAccount(AccountDto accountDto);


}
