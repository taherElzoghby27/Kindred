package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.friendship.FriendShipDto;
import com.spring.boot.social.entity.friendship.Friendship;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FriendShipMapper {
    FriendShipMapper INSTANCE = Mappers.getMapper(FriendShipMapper.class);

    FriendShipDto toFriendShipDto(Friendship friendShip);

    Friendship toFriendship(FriendShipDto friendShipDto);
}
