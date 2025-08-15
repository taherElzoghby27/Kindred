package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.AccountRepo;
import com.spring.boot.social.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AccountServiceImpl(AccountRepo accountRepo, PasswordEncoder passwordEncoder) {
        this.accountRepo = accountRepo;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public AccountDto createAccount(AccountDto accountDto) {
        if (Objects.nonNull(accountDto.getId())) {
            throw new BadRequestException("empty.account_id_must_null");
        }
        //check account if exist
        if (Objects.nonNull(getAccountByUsername(accountDto.getUsername()))) {
            throw new BadRequestException("account_already_exist");
        }
        if (Objects.isNull(accountDto.getUsername())) {
            throw new BadRequestException("empty.username");
        }
        //map accountDto to account
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
        //hash password
        String hashPassword = passwordEncoder.encode(account.getPassword());
        account.setPassword(hashPassword);
        //init auditor
        account.setCreatedBy(accountDto.getUsername());
        account.setUpdatedBy(accountDto.getUsername());
        //save in db
        account = accountRepo.save(account);
        accountDto = AccountMapper.ACCOUNT_MAPPER.toAccountDto(account);
        return accountDto;
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {//todo : add update account details for this
        if (Objects.isNull(accountDto.getId())) {
            throw new BadRequestException("empty.account_id");
        }
        if (Objects.isNull(accountDto.getUsername())) {
            throw new BadRequestException("empty.username");
        }
        //check account if exist
        AccountDto oldAccount = getAccountById(accountDto.getId());
        if (!Objects.equals(accountDto.getUsername(), oldAccount.getUsername())) {
            throw new BadRequestException("username.diff");
        }
        if (!Objects.equals(accountDto.getEmail(), oldAccount.getEmail())) {
            throw new BadRequestException("email.diff");
        }
        //map accountDto to account
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
        //save in db
        account = accountRepo.save(account);
        accountDto = AccountMapper.ACCOUNT_MAPPER.toAccountDto(account);
        return accountDto;
    }

    @Override
    public AccountDto getAccountById(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("empty.account_id");
        }
        Optional<Account> result = accountRepo.findById(id);
        if (result.isEmpty()) {
            throw new BadRequestException("account.not_found");
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
            throw new BadRequestException("account.not_found");
        }
        return AccountMapper.ACCOUNT_MAPPER.toAccountDto(result.get());
    }
}
