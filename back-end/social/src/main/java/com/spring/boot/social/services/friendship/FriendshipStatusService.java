package com.spring.boot.social.services.friendship;

import com.spring.boot.social.dto.friendship.FriendshipStatusDto;

import java.util.List;

public interface FriendshipStatusService {
    FriendshipStatusDto createFriendShipStatus(Long friendId);

    FriendshipStatusDto getFriendshipStatusByFriendId(Long friendId);

    FriendshipStatusDto getFriendshipStatusById(Long friendshipStatusId);

    void removeFriendShipStatusByFriendId(Long friendId);

    void removeFriendShipStatusById(Long id);

    void updateFriendshipStatus(Long id, String status);

    List<FriendshipStatusDto> getFriendshipStatusByStatus(String status);
}
