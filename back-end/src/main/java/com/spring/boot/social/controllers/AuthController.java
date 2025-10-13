package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.AccountDetailsDto;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.AuthService;
import com.spring.boot.social.vm.AccountFriendshipVm;
import com.spring.boot.social.vm.AccountResponseVm;
import com.spring.boot.social.vm.GeneralResponseVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RequestMapping("/auth")
@RestController
@RequiredArgsConstructor
@Tag(name = "Authentication", description = "Authentication management APIs")
public class AuthController {
    private final AuthService authService;
    private final AccountService accountService;

    @Operation(summary = "User Registration", description = "Register a new user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User created successfully",
                    content = @Content(schema = @Schema(implementation = AccountResponseVm.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "409", description = "User already exists")
    })
    @PostMapping("/sign-up")
    public SuccessDto<ResponseEntity<AccountResponseVm>> signUp(@Valid @RequestBody AccountDto accountDto) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/sign-up")).body(authService.signup(accountDto))
        );
    }

    @Operation(summary = "User Login", description = "Authenticate user and get access token")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login successful",
                    content = @Content(schema = @Schema(implementation = AccountResponseVm.class))),
            @ApiResponse(responseCode = "401", description = "Invalid credentials"),
            @ApiResponse(responseCode = "400", description = "Invalid input data")
    })
    @PostMapping("/login")
    public SuccessDto<ResponseEntity<AccountResponseVm>> login(@Valid @RequestBody AccountDto accountDto) {
        return new SuccessDto<>(
                ResponseEntity.ok(authService.login(accountDto))
        );
    }

    @Operation(summary = "Add Account Details", description = "Add additional details to user account")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account details added successfully",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PostMapping("/add-account-details")
    public SuccessDto<ResponseEntity<AccountDto>> addAccountDetails(@Valid @RequestBody AccountDetailsDto accountDetailsDto) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/add-account-details")).body(accountService.addAccountDetails(accountDetailsDto))
        );
    }

    @Operation(summary = "Update Account", description = "Update existing user account information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PutMapping("/update-account")
    public SuccessDto<ResponseEntity<AccountDto>> updateAccount(@RequestBody AccountDto accountDto) {
        return new SuccessDto<>(ResponseEntity.ok(accountService.updateAccount(accountDto)));
    }

    @Operation(summary = "get all accounts", description = "get all accounts with relationships")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "get accounts successfully",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @GetMapping("/all-account")
    public SuccessDto<ResponseEntity<GeneralResponseVm<AccountFriendshipVm>>> getUsers(
            @RequestParam int page,
            @RequestParam int size
    ) {
        return new SuccessDto<>(ResponseEntity.ok(accountService.getUsers(page, size)));
    }
}
