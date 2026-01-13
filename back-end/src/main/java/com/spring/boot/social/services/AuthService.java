package com.spring.boot.social.services;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.vm.auth.AccountResponseVm;
import com.spring.boot.social.vm.auth.LoginRequestVm;

public interface AuthService {
    AccountResponseVm login(LoginRequestVm loginRequestVm);

    AccountResponseVm signup(AccountDto accountDto);
}
