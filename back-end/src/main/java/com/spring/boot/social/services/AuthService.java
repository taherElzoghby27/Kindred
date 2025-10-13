package com.spring.boot.social.services;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.vm.AccountFriendshipVm;
import com.spring.boot.social.vm.AccountResponseVm;

import java.util.List;

public interface AuthService {
    AccountResponseVm login(AccountDto accountDto);

    AccountResponseVm signup(AccountDto accountDto);
}
