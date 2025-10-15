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
                        a1.id,a1.username,a2.id,a2.username,f.id,fstatus.status
                                    )
            FROM Account a1 join Account a2 on a1.id=:account_id and a1.id!=a2.id
            left join Friendship f on f.account.id=a1.id and f.friend.id=a2.id
            left join FriendshipStatus fs on fs.friendship.id=f.id
            left join FriendStatus fstatus on fstatus.id=fs.status.id
            """,
            countQuery = """
                    SELECT count(a1)
                    FROM Account a1 join Account a2 on a1.id=:account_id and a1.id!=a2.id
                    left join Friendship f on f.account.id=a1.id and f.friend.id=a2.id
                    left join FriendshipStatus fs on fs.friendship.id=f.id
                    left join FriendStatus fstatus on fstatus.id=fs.status.id
                    """)
    Page<AccountFriendshipVm> findAccountsWithFriendShip(@Param("account_id") Long accountId, Pageable pageable);
}
