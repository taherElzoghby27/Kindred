package com.spring.boot.social.repositories;

import com.spring.boot.social.entity.security.Account;
import com.spring.boot.social.vm.AccountFriendshipVm;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface AccountRepo extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);

    Optional<Account> findByEmail(String email);

    @Query(value = """
            SELECT new com.spring.boot.social.vm.AccountFriendshipVm(
                        new com.spring.boot.social.vm.AccountVm(a.id,a.firstName,a.lastName,null)
                                    ,fs.id
                                    ,fsT.status
                                    )
            FROM Account a
            LEFT JOIN Friendship f on a.id=f.account.id and a.id=:account_id
            LEFT JOIN f.friendshipStatus fs on fs.id=f.id
            LEFT JOIN fs.status fsT on fsT.id=fs.status.id
            """,
            countQuery = """
                    SELECT count(a)
                    FROM Account a
                    LEFT JOIN Friendship f on a.id=f.account.id and a.id=:account_id
                    LEFT JOIN f.friendshipStatus fs on fs.id=f.id
                    LEFT JOIN fs.status fsT on fsT.id=fs.status.id
                    """)
    Page<AccountFriendshipVm> findAccountsWithFriendShip(@Param("account_id") Long accountId, Pageable pageable);
}
