package com.spring.boot.social.Repo;

import com.spring.boot.social.Config.TestConfig;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.friendship.FriendStatus;
import com.spring.boot.social.entity.friendship.Friendship;
import com.spring.boot.social.entity.friendship.FriendshipStatus;
import com.spring.boot.social.repositories.AccountRepo;
import com.spring.boot.social.repositories.FriendShipRepo;
import com.spring.boot.social.repositories.FriendShipStatusRepo;
import com.spring.boot.social.repositories.FriendStatusRepo;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import com.spring.boot.social.vm.AccountFriendshipVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

@DataJpaTest
@Import(TestConfig.class)
@ActiveProfiles("test")
public class AccountRepoTest {

    @Autowired
    private AccountRepo accountRepo;

    @Autowired
    private FriendShipRepo friendShipRepo;

    @Autowired
    private FriendShipStatusRepo friendShipStatusRepo;

    @Autowired
    private FriendStatusRepo friendStatusRepo;

    private Account testAccount;

    @BeforeEach
    public void setUp() {
        testAccount = Account.builder()
                .username("taher_elzoghby")
                .email("taher@example.com")
                .firstName("Taher")
                .password("Password123!")
                .age(25L)
                .build();
        accountRepo.save(testAccount);
    }

    // 1- find account by user name with empty string will be not found.
    @Test
    public void givenEmptyUsername_whenFindByUsername_thenReturnEmpty() {
        Optional<Account> found = accountRepo.findByUsername("");
        Assertions.assertTrue(found.isEmpty());
    }

    // 2- find account by user name with number will make error (or empty as per JPA binding)
    @Test
    public void givenNumericUsername_whenFindByUsername_thenReturnEmpty() {
        Optional<Account> found = accountRepo.findByUsername("123456789");
        Assertions.assertTrue(found.isEmpty());
    }

    // 3- find account by user name with this string "or True" will make error or empty
    @Test
    public void givenSqlInjectionUsername_whenFindByUsername_thenReturnEmpty() {
        Optional<Account> found = accountRepo.findByUsername("' or '1'='1");
        Assertions.assertTrue(found.isEmpty());
    }

    // Success test for find account by username
    @Test
    public void givenExistingUsername_whenFindByUsername_thenReturnAccount() {
        Optional<Account> found = accountRepo.findByUsername("taher_elzoghby");
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("taher@example.com", found.get().getEmail());
    }

    // 4- find account by email with empty string will be not found.
    @Test
    public void givenEmptyEmail_whenFindByEmail_thenReturnEmpty() {
        Optional<Account> found = accountRepo.findByEmail("");
        Assertions.assertTrue(found.isEmpty());
    }

    // 5- find account by email with number will make error.
    @Test
    public void givenNumericEmail_whenFindByEmail_thenReturnEmpty() {
        Optional<Account> found = accountRepo.findByEmail("12345@567.com");
        Assertions.assertTrue(found.isEmpty());
    }

    // 6- find account by email with this string "or True" will make error or empty.
    @Test
    public void givenSqlInjectionEmail_whenFindByEmail_thenReturnEmpty() {
        Optional<Account> found = accountRepo.findByEmail("' or true");
        Assertions.assertTrue(found.isEmpty());
    }

    // Success test for find account by email
    @Test
    public void givenExistingEmail_whenFindByEmail_thenReturnAccount() {
        Optional<Account> found = accountRepo.findByEmail("taher@example.com");
        Assertions.assertTrue(found.isPresent());
        Assertions.assertEquals("taher_elzoghby", found.get().getUsername());
    }

    // 7- get friendships must be with 2 accounts and have status (pending,accepted,blocked)
    @Test
    public void givenFriendships_whenFindAccountsWithFriendShip_thenReturnCorrectStatus() {
        // Arrange
        Account friend = Account.builder()
                .username("friend_username")
                .email("friend@example.com")
                .firstName("Friend")
                .password("Password123!")
                .age(25L)
                .build();
        accountRepo.save(friend);

        FriendStatus pendingStatus = new FriendStatus();
        pendingStatus.setStatus(FriendStatusEnum.PENDING);
        friendStatusRepo.save(pendingStatus);

        Friendship friendship = new Friendship();
        friendship.setAccount(testAccount);
        friendship.setFriend(friend);
        friendShipRepo.save(friendship);

        FriendshipStatus fs = new FriendshipStatus();
        fs.setFriendship(friendship);
        fs.setStatus(pendingStatus);
        friendShipStatusRepo.save(fs);

        // Act
        Page<AccountFriendshipVm> result = accountRepo.findAccountsWithFriendShip(
                testAccount.getId(), PageRequest.of(0, 10)
        );

        // Assert
        Assertions.assertFalse(result.isEmpty());
        Assertions.assertEquals(FriendStatusEnum.PENDING, result.getContent().get(0).getStatus());
    }

    // 8- no data when no friendships.
    @Test
    public void givenNoOtherAccounts_whenFindAccountsWithFriendShip_thenReturnEmptyPage() {
        // Since the current query joins accounts, if only one account exists, no other accounts will be found
        Page<AccountFriendshipVm> result = accountRepo.findAccountsWithFriendShip(testAccount.getId(), PageRequest.of(0, 10));

        // The query finds all accounts EXCEPT the one with account_id. 
        // If there is only one account (testAccount), content should be empty.
        Assertions.assertTrue(result.getContent().isEmpty());
    }
}
