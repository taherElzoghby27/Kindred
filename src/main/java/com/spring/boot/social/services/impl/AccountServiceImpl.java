package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDetailsDto;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.models.security.AccountDetails;
import com.spring.boot.social.repositories.AccountRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final PasswordEncoder passwordEncoder;
    private final ActivityService activityService;


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
        account.getAccountDetails().setAccount(account);
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
        if (Objects.nonNull(accountDto.getAccountDetails())) {
            updateAccountDetails(accountDto, oldAccountDto);
        }
        return oldAccountDto;
    }

    private static void updateAccountDetails(AccountDto accountDto, AccountDto oldAccountDto) {
        AccountDetailsDto oldAccountDetailsDto = oldAccountDto.getAccountDetails();
        AccountDetailsDto accountDetailsDto = accountDto.getAccountDetails();
        if (Objects.nonNull(accountDetailsDto.getAge())) {
            oldAccountDetailsDto.setAge(accountDetailsDto.getAge());
        }
        if (Objects.nonNull(accountDetailsDto.getAddress())) {
            oldAccountDetailsDto.setAddress(accountDetailsDto.getAddress());
        }
        if (Objects.nonNull(accountDetailsDto.getBio())) {
            oldAccountDetailsDto.setBio(accountDetailsDto.getBio());
        }
        if (Objects.nonNull(accountDetailsDto.getBirthday())) {
            oldAccountDetailsDto.setBirthday(accountDetailsDto.getBirthday());
        }
        if (Objects.nonNull(accountDetailsDto.getFullName())) {
            oldAccountDetailsDto.setFullName(accountDetailsDto.getFullName());
        }
        if (Objects.nonNull(accountDetailsDto.getPhoneNumber())) {
            oldAccountDetailsDto.setPhoneNumber(accountDetailsDto.getPhoneNumber());
        }
        if (Objects.nonNull(accountDetailsDto.getProfilePictureUrl())) {
            oldAccountDetailsDto.setProfilePictureUrl(accountDetailsDto.getProfilePictureUrl());
        }
        oldAccountDto.setAccountDetails(oldAccountDetailsDto);
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

    private boolean checkAccountByUsername(String username) {
        if (Objects.isNull(username)) {
            throw new BadRequestException("empty.username");
        }
        Optional<Account> result = accountRepo.findByUsername(username);
        return result.isPresent();
    }
}
