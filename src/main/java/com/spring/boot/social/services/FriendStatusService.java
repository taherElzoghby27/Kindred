package com.spring.boot.social.services;

import com.spring.boot.social.dto.FriendStatusDto;

public interface FriendStatusService {
    FriendStatusDto getStatus(String status);
}
