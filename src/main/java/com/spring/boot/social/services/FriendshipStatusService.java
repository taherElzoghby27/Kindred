package com.spring.boot.social.services;

import com.spring.boot.social.dto.FriendshipStatusDto;

public interface FriendshipStatusService {
    FriendshipStatusDto createFriendShipStatus(Long friendId);

    FriendshipStatusDto getFriendshipStatus(Long friendId);

    void removeFriendShipStatus(Long friendId);

    void takeActionOnRequestFriendShip(Long friendId, String status);
}
