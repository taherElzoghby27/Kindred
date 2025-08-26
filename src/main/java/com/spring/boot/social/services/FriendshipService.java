package com.spring.boot.social.services;

import com.spring.boot.social.dto.FriendShipDto;

public interface FriendshipService {
    FriendShipDto createFriendShip(Long friendId);

    void removeFriendShip(Long friendId);

    void takeActionOnRequestFriendShip(Long friendId, String status);
}
