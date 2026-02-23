package com.spring.boot.social.services;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.vm.AccountFriendshipVm;
import com.spring.boot.social.vm.GeneralResponseVm;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(AccountDto accountDto);

    AccountDto getAccountDtoById(Long id);

    AccountDto getAccountDtoByUsername(String username);

    AccountDto getAccountDtoByEmail(String email);

    Account getCurrentAccount();

    Account getAccountById(Long accountId);

    Account getAccountByUsername(String userName);

    GeneralResponseVm<AccountFriendshipVm> getUsers(int page, int size);
}
