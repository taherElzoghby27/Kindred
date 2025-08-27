package com.spring.boot.social.services.friendship;

import com.spring.boot.social.dto.friendship.FriendshipStatusDto;

public interface FriendshipStatusService {
    FriendshipStatusDto createFriendShipStatus(Long friendId);

    FriendshipStatusDto getFriendshipStatusByFriendId(Long friendId);

    FriendshipStatusDto getFriendshipStatusById(Long friendshipStatusId);

    void removeFriendShipStatusByFriendId(Long friendId);

    void removeFriendShipStatusById(Long id);

    void updateFriendshipStatus(Long id, String status);
}
