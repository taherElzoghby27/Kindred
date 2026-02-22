//package com.spring.boot.social.Controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import com.spring.boot.social.config.security.TokenHandler;
//import com.spring.boot.social.dto.AccountDto;
//import com.spring.boot.social.services.AccountService;
//import com.spring.boot.social.services.impl.AuthServiceImpl;
//import com.spring.boot.social.vm.auth.LoginRequestVm;
//import org.junit.jupiter.api.BeforeAll;
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.http.MediaType;
//import org.springframework.mock.web.MockHttpServletRequest;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.hamcrest.Matchers.hasSize;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
//
//@AutoConfigureMockMvc
//@SpringBootTest // for integration testing
/// /@TestPropertySource("application-test.yml")
//public class AuthControllerTest {
//    @Mock
//    private AccountService accountService;
//
//    @Mock
//    private PasswordEncoder passwordEncoder;
//
//    @Mock
//    private TokenHandler tokenHandler;
//
//    @InjectMocks
//    private AuthServiceImpl authService;
//    @Autowired
//    private MockMvc mockMvc;
//    @Autowired
//    private ObjectMapper objectMapper;
//    //private static MockHttpServletRequest request;
//    private AccountDto accountDto;
//    public static final MediaType APP_JSON_UTF8 = MediaType.APPLICATION_JSON;
//
////    @BeforeAll
////    public static void setup() {
////        request = new MockHttpServletRequest();
////        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZE1vaGFtZWQ4OTdAZ21haWwuY29tIiwiaWF0IjoxNzcwNDkyNjY5LCJleHAiOjE3NzMwODQ2Njl9.cWreCpffrw99XhtDfaHFSPlXuhO6bc8IPpolalW84zQ");
////    }
//
//    @BeforeEach
//    public void setupMock() {
//        accountDto = new AccountDto();
//        accountDto.setUsername("us1rnwm1ername");
//        accountDto.setFirstName("tah1r1win");
//        accountDto.setLastName("ami11n1nnwn");
//        accountDto.setEmail("taheramiwa21@gmail.com");
//        accountDto.setAddress("cairo");
//        accountDto.setBio("se");
//        accountDto.setFullName("taher a1sn elzoghby");
//        accountDto.setPhoneNumber("01023481231");
//        accountDto.setPassword("taherTAHER2742002@#$");
//    }
//
//    @Test
//    public void givenAccountDto_whenSignUp_thenSuccess() throws Exception {
//        mockMvc.perform(
//                post("/auth/sign-up")
//                        .contentType(APP_JSON_UTF8)
//                        .content(objectMapper.writeValueAsString(accountDto))
//        ).andExpect(status().isCreated());
//        //.andExpect(jsonPath("$", hasSize(1)));
//    }
//
//    @Test
//    public void givenAccountDto_whenLogin_thenSuccess() throws Exception {
//        LoginRequestVm loginRequestVm = new LoginRequestVm();
//        loginRequestVm.setEmail("ahmedMohamed897@gmail.com");
//        loginRequestVm.setPassword("taherTAHER2742002@#$");
//        mockMvc.perform(
//                post("/auth/login")
//                        .contentType(APP_JSON_UTF8)
//                        .content(objectMapper.writeValueAsString(loginRequestVm))
//        ).andExpect(status().isOk());
//    }
//
//    @Test
//    public void givenPageAndSide_whenGetUsers_thenSuccess() throws Exception {
//        mockMvc.perform(
//                        get("/auth")
//                                .contentType(APP_JSON_UTF8)
//                                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZE1vaGFtZWQ4OTdAZ21haWwuY29tIiwiaWF0IjoxNzcwNDkyNjY5LCJleHAiOjE3NzMwODQ2Njl9.cWreCpffrw99XhtDfaHFSPlXuhO6bc8IPpolalW84zQ")
//                                .param("page", "1")
//                                .param("size", "5")
//                ).andExpect(status().isOk())
//                .andExpect(jsonPath("$.data.body.data", hasSize(5)));
//    }
//}
//unit test for auth controller
package com.spring.boot.social.Controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.boot.social.config.security.TokenHandler;
import com.spring.boot.social.controllers.AuthController;
import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.repositories.AccountRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.impl.AuthServiceImpl;
import com.spring.boot.social.vm.auth.LoginRequestVm;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.task.TaskExecutionAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@AutoConfigureMockMvc
//@SpringBootTest
@WebMvcTest(value = AuthController.class,excludeAutoConfiguration = {
        DataSourceAutoConfiguration.class,
        HibernateJpaAutoConfiguration.class,
        TaskExecutionAutoConfiguration.class
})//for unit testing
//@TestPropertySource("application-test.yml")
public class AuthControllerTest {
    @MockitoBean
    private AccountService accountService;

    @MockitoBean
    private PasswordEncoder passwordEncoder;

    @MockitoBean
    private TokenHandler tokenHandler;

    @MockitoBean
    private AuthServiceImpl authService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    //private static MockHttpServletRequest request;
    private AccountDto accountDto;
    public static final MediaType APP_JSON_UTF8 = MediaType.APPLICATION_JSON;

//    @BeforeAll
//    public static void setup() {
//        request = new MockHttpServletRequest();
//        request.addHeader("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZE1vaGFtZWQ4OTdAZ21haWwuY29tIiwiaWF0IjoxNzcwNDkyNjY5LCJleHAiOjE3NzMwODQ2Njl9.cWreCpffrw99XhtDfaHFSPlXuhO6bc8IPpolalW84zQ");
//    }

    @BeforeEach
    public void setupMock() {
        accountDto = new AccountDto();
        accountDto.setUsername("us1rn4wm1ername");
        accountDto.setFirstName("tah1r14win");
        accountDto.setLastName("ami411n1nnwn");
        accountDto.setEmail("tahera4miwa21@gmail.com");
        accountDto.setAddress("cairo");
        accountDto.setBio("se");
        accountDto.setFullName("taher a1sn elzoghby");
        accountDto.setPhoneNumber("01023481231");
        accountDto.setPassword("taherTAHER2742002@#$");
    }

    @Test
    public void givenAccountDto_whenSignUp_thenSuccess() throws Exception {
        mockMvc.perform(
                post("/auth/sign-up")
                        .contentType(APP_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(accountDto))
        ).andExpect(status().isCreated());
        //.andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void givenAccountDto_whenLogin_thenSuccess() throws Exception {
        LoginRequestVm loginRequestVm = new LoginRequestVm();
        loginRequestVm.setEmail("ahmedMohamed897@gmail.com");
        loginRequestVm.setPassword("taherTAHER2742002@#$");
        mockMvc.perform(
                post("/auth/login")
                        .contentType(APP_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(loginRequestVm))
        ).andExpect(status().isOk());
    }

    @Test
    public void givenPageAndSide_whenGetUsers_thenSuccess() throws Exception {
        mockMvc.perform(
                        get("/auth")
                                .contentType(APP_JSON_UTF8)
                                .header("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhaG1lZE1vaGFtZWQ4OTdAZ21haWwuY29tIiwiaWF0IjoxNzcwNDkyNjY5LCJleHAiOjE3NzMwODQ2Njl9.cWreCpffrw99XhtDfaHFSPlXuhO6bc8IPpolalW84zQ")
                                .param("page", "1")
                                .param("size", "5")
                ).andExpect(status().isOk())
                .andExpect(jsonPath("$.data.body.data", hasSize(5)));
    }
}
