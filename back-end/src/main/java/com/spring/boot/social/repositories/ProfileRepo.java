package com.spring.boot.social.repositories;

import com.spring.boot.social.entity.security.Account;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Account, Long> {
    @Query(value = """
            select count(f) from FriendshipStatus f
            where (f.friendship.account.id=:accountId or f.friendship.friend.id=:accountId)
                        and  :status = f.status.status
            """)
    Long nOfFriends(
            @Param("accountId") Long accountId,
            @Param("status") FriendStatusEnum status
    );
}
