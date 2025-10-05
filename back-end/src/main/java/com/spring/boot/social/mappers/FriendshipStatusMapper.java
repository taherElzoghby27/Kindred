package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.friendship.FriendshipStatusDto;
import com.spring.boot.social.entity.friendship.FriendshipStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendshipStatusMapper {
    FriendshipStatusMapper INSTANCE = Mappers.getMapper(FriendshipStatusMapper.class);

    FriendshipStatus toFriendshipStatus(FriendshipStatusDto friendShipStatusDto);

    FriendshipStatusDto toFriendshipStatusDto(FriendshipStatus friendshipStatus);
}
