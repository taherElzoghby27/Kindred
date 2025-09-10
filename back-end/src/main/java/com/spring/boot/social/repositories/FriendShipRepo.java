package com.spring.boot.social.repositories;

import com.spring.boot.social.models.friendship.Friendship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendShipRepo extends JpaRepository<Friendship, Long> {
    @Query(value = """
            select f from Friendship f
             where (:accountId=f.account.id and :friendId=f.friend.id)
             or (:accountId=f.friend.id and :friendId=f.account.id)
            """)
    Optional<Friendship> findFriendshipBetweenAccounts(@Param("accountId") Long accountId, @Param("friendId") Long friendId);

    @Modifying
    @Query(value = "delete from Friendship f where f.id=:id")
    void deleteFriendShipById(Long id);
}
