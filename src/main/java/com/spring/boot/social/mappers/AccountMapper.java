package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.AccountDetailsDto;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.models.security.AccountDetails;
import com.spring.boot.social.vm.AccountResponseVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface AccountMapper {
    AccountMapper ACCOUNT_MAPPER = Mappers.getMapper(AccountMapper.class);

    AccountDto toAccountDto(Account account);

    Account toAccount(AccountDto accountDto);

    AccountResponseVm toAccountResponseVm(AccountDto accountDto);

    @Mapping(source = "accountId", target = "account", ignore = true)
    AccountDetails toAccountDetails(AccountDetailsDto accountDetailsDto);

    @Mapping(source = "account", target = "accountId", qualifiedByName = "accountToId")
    AccountDetailsDto toAccountDetailsDto(AccountDetails accountDetails);

    @Named("accountToId")
    default Long accountToId(Account account) {
        return account == null ? null : account.getId();
    }
}
