package com.spring.boot.social.repositories;

import com.spring.boot.social.models.friendship.FriendStatus;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FriendStatusRepo extends JpaRepository<FriendStatus, Long> {
    Optional<FriendStatus> findByStatus(FriendStatusEnum status);
}
