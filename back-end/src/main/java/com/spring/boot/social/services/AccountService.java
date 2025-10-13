package com.spring.boot.social.services;

import com.spring.boot.social.dto.AccountDetailsDto;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.entity.security.Account;
import com.spring.boot.social.vm.AccountFriendshipVm;
import com.spring.boot.social.vm.GeneralResponseVm;

import java.util.List;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(AccountDto accountDto);

    AccountDto addAccountDetails(AccountDetailsDto accountDetailsDto);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByUsername(String username);

    AccountDto getAccountByEmail(String email);

    Account getCurrentAccount();

    Account getAccount(Long accountId);

    GeneralResponseVm<AccountFriendshipVm> getUsers(int page, int size);
}
