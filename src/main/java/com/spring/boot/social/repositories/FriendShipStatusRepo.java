package com.spring.boot.social.repositories;
import com.spring.boot.social.models.friendship.FriendshipStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FriendShipStatusRepo extends JpaRepository<FriendshipStatus, Long> {
}
