package com.spring.boot.social.services.impl;

import com.spring.boot.social.config.security.TokenHandler;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.AuthService;
import com.spring.boot.social.vm.AccountResponseVm;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final AccountService accountService;
    private final PasswordEncoder passwordEncoder;
    private final TokenHandler tokenHandler;

    @Override
    public AccountResponseVm login(AccountDto accountDto) {
        if (Objects.isNull(accountDto.getPassword())) {
            throw new BadRequestException("empty.password");
        }
        AccountDto accountExist = accountService.getAccountByEmail(accountDto.getEmail());
        if (!passwordEncoder.matches(accountDto.getPassword(), accountExist.getPassword())) {
            throw new BadRequestException("wrong.password");
        }
        return getAccountResponseVm(accountExist);
    }

    private AccountResponseVm getAccountResponseVm(AccountDto accountDto) {
        //generate token
        String token = tokenHandler.generateToken(accountDto);
        AccountResponseVm accountResponseVm = AccountMapper.ACCOUNT_MAPPER.toAccountResponseVm(accountDto);
        //set token to response
        accountResponseVm.setToken(token);
        return accountResponseVm;
    }

    @Override
    public AccountResponseVm signup(AccountDto accountDto) {
        validationForSignUp(accountDto);
        accountDto = accountService.createAccount(accountDto);
        return getAccountResponseVm(accountDto);
    }

    private static void validationForSignUp(AccountDto accountDto) {
        if (Objects.isNull(accountDto.getUsername()) || accountDto.getUsername().isEmpty()) {
            throw new BadRequestException("empty.username");
        }
        int lengthUsername = accountDto.getUsername().length();
        if (!(lengthUsername >= 12 && lengthUsername <= 50)) {
            throw new BadRequestException("length.username");
        }
        if (Objects.isNull(accountDto.getPassword()) || accountDto.getPassword().isEmpty()) {
            throw new BadRequestException("empty.password");
        }
    }
}
