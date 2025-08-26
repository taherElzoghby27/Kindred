package com.spring.boot.social.services;

public interface FriendshipService {
    void createFriendShip(Long friendId);

    void removeFriendShip(Long friendId);

    void takeActionOnRequestFriendShip(Long friendId, String status);
}
