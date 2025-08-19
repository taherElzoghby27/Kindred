package com.spring.boot.social.controllers;


import com.spring.boot.social.dto.AccountDetailsDto;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.AuthService;
import com.spring.boot.social.vm.AccountResponseVm;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

@RequestMapping("/auth")
@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private AccountService accountService;

    @PostMapping("/sign-up")
    public ResponseEntity<AccountResponseVm> signUp(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.created(URI.create("/sign-up")).body(authService.signup(accountDto));
    }

    @PostMapping("/login")
    public ResponseEntity<AccountResponseVm> login(@Valid @RequestBody AccountDto accountDto) {
        return ResponseEntity.ok(authService.login(accountDto));
    }

    @PostMapping("/add-account-details")
    public ResponseEntity<AccountDto> addAccountDetails(@Valid @RequestBody AccountDetailsDto accountDetailsDto) {
        return ResponseEntity.created(
                URI.create("/add-account-details")
        ).body(
                accountService.addAccountDetails(accountDetailsDto)
        );
    }
}
