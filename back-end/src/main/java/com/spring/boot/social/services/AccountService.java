package com.spring.boot.social.services;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.vm.AccountFriendshipVm;
import com.spring.boot.social.vm.GeneralResponseVm;

public interface AccountService {
    AccountDto createAccount(AccountDto accountDto);

    AccountDto updateAccount(AccountDto accountDto);

    AccountDto getAccountById(Long id);

    AccountDto getAccountByUsername(String username);

    AccountDto getAccountByEmail(String email);

    Account getCurrentAccount();

    Account getAccount(Long accountId);

    GeneralResponseVm<AccountFriendshipVm> getUsers(int page, int size);
}
