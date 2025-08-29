package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.AccountDetailsDto;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.AuthService;
import com.spring.boot.social.vm.AccountResponseVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    private final AccountService accountService;

    @PostMapping("/sign-up")
    public SuccessDto<ResponseEntity<AccountResponseVm>> signUp(@Valid @RequestBody AccountDto accountDto) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/sign-up")).body(authService.signup(accountDto))
        );
    }

    @PostMapping("/login")
    public SuccessDto<ResponseEntity<AccountResponseVm>> login(@Valid @RequestBody AccountDto accountDto) {
        return new SuccessDto<>(
                ResponseEntity.ok(authService.login(accountDto))
        );
    }

    @PostMapping("/add-account-details")
    public SuccessDto<ResponseEntity<AccountDto>> addAccountDetails(@Valid @RequestBody AccountDetailsDto accountDetailsDto) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/add-account-details")).body(accountService.addAccountDetails(accountDetailsDto))
        );
    }

    @PutMapping("/update-account")
    public SuccessDto<ResponseEntity<AccountDto>> updateAccount(@RequestBody AccountDto accountDto) {
        return new SuccessDto<>(ResponseEntity.ok(accountService.updateAccount(accountDto)));
    }
}
