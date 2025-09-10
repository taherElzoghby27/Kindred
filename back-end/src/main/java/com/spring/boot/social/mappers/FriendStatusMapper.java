package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.friendship.FriendStatusDto;
import com.spring.boot.social.models.friendship.FriendStatus;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendStatusMapper {
    FriendStatusMapper INSTANCE = Mappers.getMapper(FriendStatusMapper.class);

    FriendStatusDto toFriendStatusDto(FriendStatus friendStatus);

    FriendStatus toFriendStatus(FriendStatusDto friendStatusDto);
}
