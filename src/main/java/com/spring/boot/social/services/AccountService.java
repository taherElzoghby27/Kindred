package com.spring.boot.social.services;

import com.spring.boot.social.dto.AccountDto;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByUsername(String username);

    AccountDto getAccountByEmail(String email);
}
