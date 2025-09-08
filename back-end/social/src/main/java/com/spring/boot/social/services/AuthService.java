package com.spring.boot.social.services;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.vm.AccountResponseVm;

public interface AuthService {
    AccountResponseVm login(AccountDto accountDto);

    AccountResponseVm signup(AccountDto accountDto);
}
