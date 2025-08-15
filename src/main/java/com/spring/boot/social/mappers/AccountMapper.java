package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.models.security.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    @Mapping(target = "password", ignore = true)
    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);
}
