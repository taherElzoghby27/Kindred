package com.spring.boot.social.repositories;

import com.spring.boot.social.models.friendship.FriendshipStatus;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FriendShipStatusRepo extends JpaRepository<FriendshipStatus, Long> {
    @Modifying
    @Query(value = "delete from FriendshipStatus f where f.id=:id")
    void deleteFriendshipStatusById(Long id);

    Optional<FriendshipStatus> findByFriendshipId(Long friendshipId);

    @Query(value = """
            select f from FriendshipStatus f
            where f.friendship.friend.id=:accountId and  :status = f.status.status
            """)
    List<FriendshipStatus> findAllByAccountIdAndStatus(@Param("accountId") Long accountId, @Param("status") FriendStatusEnum status);
}
