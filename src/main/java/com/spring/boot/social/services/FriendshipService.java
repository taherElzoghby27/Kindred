package com.spring.boot.social.services;

import com.spring.boot.social.dto.FriendShipDto;

public interface FriendshipService {
    FriendShipDto createFriendShip(Long friendId);
}
