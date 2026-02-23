package com.spring.boot.social.Service;

import com.spring.boot.social.config.security.TokenHandler;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.ConflictException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.impl.AuthServiceImpl;
import com.spring.boot.social.vm.auth.AccountResponseVm;
import com.spring.boot.social.vm.auth.LoginRequestVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {

    @Mock
    private AccountService accountService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private TokenHandler tokenHandler;

    @InjectMocks
    private AuthServiceImpl authService;

    private AccountDto accountDto;
    private LoginRequestVm loginRequest;

    @BeforeEach
    public void setUp() {
        accountDto = new AccountDto();
        accountDto.setUsername("testuser123456");
        accountDto.setEmail("test@example.com");
        accountDto.setPassword("Password123!");
        accountDto.setFirstName("Test");

        loginRequest = new LoginRequestVm();
        loginRequest.setEmail("test@example.com");
        loginRequest.setPassword("Password123!");
    }

    // 13- login with existed email and password will be login
    @Test
    public void givenCorrectCredentials_whenLogin_thenSuccess() {
        when(accountService.getAccountDtoByEmail(loginRequest.getEmail())).thenReturn(accountDto);
        when(passwordEncoder.matches(loginRequest.getPassword(), accountDto.getPassword())).thenReturn(true);
        when(tokenHandler.generateToken(any(AccountDto.class))).thenReturn("fake-token");

        AccountResponseVm response = authService.login(loginRequest);

        Assertions.assertNotNull(response.getToken());
        Assertions.assertEquals(accountDto.getEmail(), response.getEmail());
    }

    // 14- login with wrong password will error
    @Test
    public void givenWrongPassword_whenLogin_thenThrowException() {
        when(accountService.getAccountDtoByEmail(loginRequest.getEmail())).thenReturn(accountDto);
        when(passwordEncoder.matches(loginRequest.getPassword(), accountDto.getPassword())).thenReturn(false);

        Assertions.assertThrows(BadRequestException.class, () -> authService.login(loginRequest));
    }

    // 15- login with wrong email will error
    @Test
    public void givenWrongEmail_whenLogin_thenThrowException() {
        when(accountService.getAccountDtoByEmail(loginRequest.getEmail())).thenThrow(new NotFoundResourceException("account.not_found"));

        Assertions.assertThrows(NotFoundResourceException.class, () -> authService.login(loginRequest));
    }

    // 16- sign up with data and success
    @Test
    public void givenValidData_whenSignup_thenSuccess() {
        when(accountService.createAccount(any(AccountDto.class))).thenReturn(accountDto);
        when(tokenHandler.generateToken(any(AccountDto.class))).thenReturn("fake-token");

        AccountResponseVm response = authService.signup(accountDto);

        Assertions.assertNotNull(response.getToken());
    }

    // 17- sign up with existed data will fail
    @Test
    public void givenExistedData_whenSignup_thenFail() {
        when(accountService.createAccount(any(AccountDto.class))).thenThrow(new ConflictException("account_already_exist"));

        Assertions.assertThrows(ConflictException.class, () -> authService.signup(accountDto));
    }
}
