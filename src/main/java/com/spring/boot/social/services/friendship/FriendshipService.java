package com.spring.boot.social.services.friendship;

import com.spring.boot.social.dto.friendship.FriendShipDto;

public interface FriendshipService {
    FriendShipDto createFriendShip(Long friendId);

    FriendShipDto getFriendShip(Long friendId);

    void removeFriendShip(Long friendId);
}
