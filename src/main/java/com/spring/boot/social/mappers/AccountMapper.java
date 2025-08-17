package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.vm.AccountResponseVm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);

    AccountResponseVm toAccountResponseVm(AccountDto accountDto);
}
