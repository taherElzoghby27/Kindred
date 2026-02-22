package com.spring.boot.social.Service;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.ConflictException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.repositories.AccountRepo;
import com.spring.boot.social.services.impl.AccountServiceImpl;
import com.spring.boot.social.vm.AccountFriendshipVm;
import com.spring.boot.social.vm.GeneralResponseVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AccountServiceTest {

    @Mock
    private AccountRepo accountRepo;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AccountServiceImpl accountService;

    private AccountDto accountDto;
    private Account account;

    @BeforeEach
    public void setUp() {
        accountDto = new AccountDto();
        accountDto.setUsername("testuser123456"); // min 12 chars
        accountDto.setEmail("test@example.com");
        accountDto.setPassword("Password123!");
        accountDto.setFirstName("Test");
        accountDto.setLastName("User");

        account = Account.builder()
                .username("testuser123456")
                .email("test@example.com")
                .password("hashedPassword")
                .firstName("Test")
                .lastName("User")
                .enabled(1L)
                .build();
        account.setId(1L);
    }

    // 1- create account with required data and without id will success
    @Test
    public void givenAccountDtoWithoutId_whenCreateAccount_thenSuccess() {
        when(accountRepo.findByUsername(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(accountRepo.save(any(Account.class))).thenReturn(account);

        AccountDto savedAccount = accountService.createAccount(accountDto);

        Assertions.assertNotNull(savedAccount.getId());
        Assertions.assertEquals(accountDto.getUsername(), savedAccount.getUsername());
    }

    // 2- create account with unique username and unique email will success
    @Test
    public void givenUniqueUsernameAndEmail_whenCreateAccount_thenSuccess() {
        when(accountRepo.findByUsername(accountDto.getUsername())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(anyString())).thenReturn("hashedPassword");
        when(accountRepo.save(any(Account.class))).thenReturn(account);

        AccountDto savedAccount = accountService.createAccount(accountDto);

        Assertions.assertEquals(accountDto.getEmail(), savedAccount.getEmail());
    }

    // 3- create account with existed email and username will crash
    @Test
    public void givenExistingUsername_whenCreateAccount_thenThrowException() {
        when(accountRepo.findByUsername(accountDto.getUsername())).thenReturn(Optional.of(account));

        Assertions.assertThrows(ConflictException.class, () -> accountService.createAccount(accountDto));
    }

    // 4- update account without id will be fail
    // Note: Implementation logic uses SecurityUtils.getCurrentAccount(). If that principal has no ID, getAccountById fails.
    @Test
    public void givenAuthWithoutId_whenUpdateAccount_thenFail() {
        AccountDto authDto = new AccountDto();
        authDto.setId(null);

        try (MockedStatic<com.spring.boot.social.utils.SecurityUtils> mockedSecurityUtils = mockStatic(com.spring.boot.social.utils.SecurityUtils.class)) {
            mockedSecurityUtils.when(com.spring.boot.social.utils.SecurityUtils::getCurrentAccount).thenReturn(authDto);

            Assertions.assertThrows(BadRequestException.class, () -> accountService.updateAccount(accountDto));
        }
    }

    // 5- update account with id will be success and data must be updated with request model
    @Test
    public void givenAuthWithId_whenUpdateAccount_thenSuccess() {
        AccountDto authDto = new AccountDto();
        authDto.setId(1L);

        AccountDto updateRequest = new AccountDto();
        updateRequest.setFirstName("UpdatedName");

        try (MockedStatic<com.spring.boot.social.utils.SecurityUtils> mockedSecurityUtils = mockStatic(com.spring.boot.social.utils.SecurityUtils.class)) {
            mockedSecurityUtils.when(com.spring.boot.social.utils.SecurityUtils::getCurrentAccount).thenReturn(authDto);

            when(accountRepo.findById(1L)).thenReturn(Optional.of(account));
            // Use thenAnswer to return the account object that was manipulated by the service
            when(accountRepo.save(any(Account.class))).thenAnswer(invocation -> invocation.getArgument(0));

            AccountDto result = accountService.updateAccount(updateRequest);
            Assertions.assertEquals("UpdatedName", result.getFirstName());
        }
    }

    // 6- get account by existed id will success
    @Test
    public void givenExistedId_whenGetAccountById_thenSuccess() {
        when(accountRepo.findById(1L)).thenReturn(Optional.of(account));

        AccountDto result = accountService.getAccountById(1L);
        Assertions.assertEquals(1L, result.getId());
    }

    // 7- get account by new id will empty (throws exception)
    @Test
    public void givenNewId_whenGetAccountById_thenThrowException() {
        when(accountRepo.findById(99L)).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundResourceException.class, () -> accountService.getAccountById(99L));
    }

    // 8- get account by existed username will success
    @Test
    public void givenExistedUsername_whenGetAccountByUsername_thenSuccess() {
        when(accountRepo.findByUsername("testuser123456")).thenReturn(Optional.of(account));

        AccountDto result = accountService.getAccountByUsername("testuser123456");
        Assertions.assertEquals("testuser123456", result.getUsername());
    }

    // 9- get account by new username will empty (throws exception)
    @Test
    public void givenNewUsername_whenGetAccountByUsername_thenThrowException() {
        when(accountRepo.findByUsername("newuser")).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundResourceException.class, () -> accountService.getAccountByUsername("newuser"));
    }

    // 10- get account by existed email will success
    @Test
    public void givenExistedEmail_whenGetAccountByEmail_thenSuccess() {
        when(accountRepo.findByEmail("test@example.com")).thenReturn(Optional.of(account));

        AccountDto result = accountService.getAccountByEmail("test@example.com");
        Assertions.assertEquals("test@example.com", result.getEmail());
    }

    // 11- get account by new email will empty (throws exception)
    @Test
    public void givenNewEmail_whenGetAccountByEmail_thenThrowException() {
        when(accountRepo.findByEmail("new@example.com")).thenReturn(Optional.empty());

        Assertions.assertThrows(NotFoundResourceException.class, () -> accountService.getAccountByEmail("new@example.com"));
    }

    // 12- get users with pagination(page, size) will success
    @Test
    public void givenPageAndSize_whenGetUsers_thenSuccess() {
        AccountDto authDto = new AccountDto();
        authDto.setId(1L);

        AccountFriendshipVm vm = new AccountFriendshipVm();
        Page<AccountFriendshipVm> page = new PageImpl<>(Collections.singletonList(vm));

        when(accountRepo.findAccountsWithFriendShip(Mockito.eq(1L), any(Pageable.class))).thenReturn(page);

        try (MockedStatic<com.spring.boot.social.utils.SecurityUtils> mockedSecurityUtils = mockStatic(com.spring.boot.social.utils.SecurityUtils.class)) {
            mockedSecurityUtils.when(com.spring.boot.social.utils.SecurityUtils::getCurrentAccount).thenReturn(authDto);

            // PaginationHelper likely expects 1-based page
            GeneralResponseVm<AccountFriendshipVm> result = accountService.getUsers(1, 10);
            Assertions.assertFalse(result.getData().isEmpty());
        }
    }
}
