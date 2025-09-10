//package com.spring.boot.social.authentication;
//
//import com.spring.boot.social.dto.AccountDto;
//import com.spring.boot.social.mappers.AccountMapper;
//import com.spring.boot.social.models.security.Account;
//import com.spring.boot.social.repositories.AccountRepo;
//import com.spring.boot.social.services.AccountService;
//import com.spring.boot.social.services.impl.AccountServiceImpl;
//import org.junit.jupiter.api.DisplayName;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.BDDMockito;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.junit.jupiter.MockitoExtension;
//import org.springframework.security.crypto.password.PasswordEncoder;
//
//@ExtendWith(MockitoExtension.class)
//public class CreateAccountUnitTests {
//    @Mock
//    private AccountRepo accountRepo;
//    @Mock
//    private PasswordEncoder passwordEncoder;
//    @InjectMocks
//    private AccountServiceImpl accountService;
//
//    @Test
//    @DisplayName(value = "test create account with database level")
//    void createAccountTest() {
//        AccountDto accountDto = new AccountDto();
//        accountDto.setUsername("taherElzoghby57");
//        accountDto.setPassword("taherTAHER2742002@#$");
//        accountDto.setEmail("taheramin442@gmail.com");
//        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
//        BDDMockito.given(accountRepo.save(account)).willReturn(account);
//        accountService.createAccount(accountDto);
//    }
//}
