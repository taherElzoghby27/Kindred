package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.repositories.AccountRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.utils.PaginationHelper;
import com.spring.boot.social.utils.SecurityUtils;
import com.spring.boot.social.vm.AccountFriendshipVm;
import com.spring.boot.social.vm.GeneralResponseVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;

    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        validateCreateAccount(accountDto);
        //map accountDto to account
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
        //hash password
        String hashPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(hashPassword);
        //init auditor
        account.setCreatedBy(accountDto.getUsername());
        account.setUpdatedBy(accountDto.getUsername());
        //enable account
        account.setEnabled(1L);
        //save in db
        account = accountRepo.save(account);
        accountDto = AccountMapper.ACCOUNT_MAPPER.toAccountDto(account);
        return accountDto;
    }

    private void validateCreateAccount(AccountDto accountDto) {
        if (Objects.nonNull(accountDto.getId())) {
            throw new BadRequestException("empty.account_id_must_null");
        }
        //check account if exist
        if (checkAccountByUsername(accountDto.getUsername())) {
            throw new NotFoundResourceException("account_already_exist");
        }
        if (Objects.isNull(accountDto.getUsername())) {
            throw new NotFoundResourceException("empty.username");
        }
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {
        AccountDto oldAccountDto = updateAccountModel(accountDto);
        //map accountDto to account
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(oldAccountDto);
        //save in db
        account = accountRepo.save(account);
        oldAccountDto = AccountMapper.ACCOUNT_MAPPER.toAccountDto(account);
        return oldAccountDto;
    }

    private AccountDto updateAccountModel(AccountDto accountDto) {
        AccountDto currentAccountDto = SecurityUtils.getCurrentAccount();
        //check account if exist
        AccountDto oldAccountDto = getAccountById(currentAccountDto.getId());
        if (Objects.nonNull(accountDto.getFirstName())) {
            oldAccountDto.setFirstName(accountDto.getFirstName());
        }
        if (Objects.nonNull(accountDto.getLastName())) {
            oldAccountDto.setLastName(accountDto.getLastName());
        }
        if (Objects.nonNull(accountDto.getAge())) {
            oldAccountDto.setAge(accountDto.getAge());
        }
        if (Objects.nonNull(accountDto.getAddress())) {
            oldAccountDto.setAddress(accountDto.getAddress());
        }
        if (Objects.nonNull(accountDto.getBio())) {
            oldAccountDto.setBio(accountDto.getBio());
        }
        if (Objects.nonNull(accountDto.getBirthday())) {
            oldAccountDto.setBirthday(accountDto.getBirthday());
        }
        if (Objects.nonNull(accountDto.getFullName())) {
            oldAccountDto.setFullName(accountDto.getFullName());
        }
        if (Objects.nonNull(accountDto.getPhoneNumber())) {
            oldAccountDto.setPhoneNumber(accountDto.getPhoneNumber());
        }
        if (Objects.nonNull(accountDto.getProfilePictureUrl())) {
            oldAccountDto.setProfilePictureUrl(accountDto.getProfilePictureUrl());
        }
        return oldAccountDto;
    }

    @Override
    public AccountDto getAccountById(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("empty.account_id");
        }
        Optional<Account> result = accountRepo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("account.not_found");
        }
        return AccountMapper.ACCOUNT_MAPPER.toAccountDto(result.get());
    }

    @Override
    public AccountDto getAccountByUsername(String username) {
        if (Objects.isNull(username)) {
            throw new BadRequestException("empty.username");
        }
        Optional<Account> result = accountRepo.findByUsername(username);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("account.not_found");
        }
        return AccountMapper.ACCOUNT_MAPPER.toAccountDto(result.get());
    }

    @Override
    public AccountDto getAccountByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new BadRequestException("empty.email");
        }
        Optional<Account> result = accountRepo.findByEmail(email);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("account.not_found");
        }
        return AccountMapper.ACCOUNT_MAPPER.toAccountDto(result.get());
    }

    @Override
    public Account getCurrentAccount() {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        return getAccount(accountDto.getId());
    }

    @Override
    public Account getAccount(Long accountId) {
        AccountDto accountDto = getAccountById(accountId);
        return AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
    }

    @Override
    public GeneralResponseVm<AccountFriendshipVm> getUsers(int page, int size) {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        Pageable pageable = PaginationHelper.getPageable(page, size);
        Page<AccountFriendshipVm> accounts = accountRepo.findAccountsWithFriendShip(accountDto.getId(), pageable);
        if (accounts.isEmpty()) {
            throw new NotFoundResourceException("account.not_found");
        }
        List<AccountFriendshipVm> accountsResult = accounts.getContent();
        return new GeneralResponseVm<>(accountsResult, accounts.getNumber() + 1, accounts.getSize());
    }

    private boolean checkAccountByUsername(String username) {
        if (Objects.isNull(username)) {
            throw new BadRequestException("empty.username");
        }
        Optional<Account> result = accountRepo.findByUsername(username);
        return result.isPresent();
    }
}
