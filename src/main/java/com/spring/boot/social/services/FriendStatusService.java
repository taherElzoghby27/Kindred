package com.spring.boot.social.services;

import com.spring.boot.social.dto.FriendStatusDto;
import com.spring.boot.social.utils.enums.FriendStatusEnum;

public interface FriendStatusService {
    FriendStatusDto getStatus(FriendStatusEnum status);
}
