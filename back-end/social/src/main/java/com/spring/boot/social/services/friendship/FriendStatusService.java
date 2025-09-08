package com.spring.boot.social.services.friendship;

import com.spring.boot.social.dto.friendship.FriendStatusDto;
import com.spring.boot.social.utils.enums.FriendStatusEnum;

public interface FriendStatusService {
    FriendStatusDto getStatus(FriendStatusEnum status);
}
