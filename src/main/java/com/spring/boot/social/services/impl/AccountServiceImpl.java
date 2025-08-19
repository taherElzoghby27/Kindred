package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDetailsDto;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.models.security.AccountDetails;
import com.spring.boot.social.repositories.AccountRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.utils.SecurityUtils;
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
            throw new BadRequestException("account_already_exist");
        }
        if (Objects.isNull(accountDto.getUsername())) {
            throw new BadRequestException("empty.username");
        }
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {//todo : add update account details for this
        validateUpdateAccount(accountDto);
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
    public AccountDto addAccountDetails(AccountDetailsDto accountDetailsDto) {
        //get account from context
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        //get account by id
        accountDto = getAccountById(accountDto.getId());
        if (Objects.nonNull(accountDetailsDto.getId())) {
            throw new BadRequestException("empty.account_details_id_must_null");
        }
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
        AccountDetails accountDetails = AccountMapper.ACCOUNT_MAPPER.toAccountDetails(accountDetailsDto);
        //add account details to account
        account.setAccountDetails(accountDetails);
        accountDetails.setAccount(account);
        account = accountRepo.save(account);
        accountDto = AccountMapper.ACCOUNT_MAPPER.toAccountDto(account);
        return accountDto;
    }

    private static void validateUpdateAccount(AccountDto accountDto) {
        if (Objects.isNull(accountDto.getId())) {
            throw new BadRequestException("empty.account_id");
        }
        if (Objects.isNull(accountDto.getUsername())) {
            throw new BadRequestException("empty.username");
        }
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

    @Override
    public AccountDto getAccountByEmail(String email) {
        if (Objects.isNull(email)) {
            throw new BadRequestException("empty.email");
        }
        Optional<Account> result = accountRepo.findByEmail(email);
        if (result.isEmpty()) {
            throw new BadRequestException("account.not_found");
        }
        return AccountMapper.ACCOUNT_MAPPER.toAccountDto(result.get());
    }

    private boolean checkAccountByUsername(String username) {
        if (Objects.isNull(username)) {
            throw new BadRequestException("empty.username");
        }
        Optional<Account> result = accountRepo.findByUsername(username);
        return result.isPresent();
    }
}
