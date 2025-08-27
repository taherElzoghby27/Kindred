package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.FriendShipDto;
import com.spring.boot.social.dto.FriendshipStatusDto;
import com.spring.boot.social.dto.FriendStatusDto;
import com.spring.boot.social.mappers.FriendShipMapper;
import com.spring.boot.social.mappers.FriendStatusMapper;
import com.spring.boot.social.mappers.FriendshipStatusMapper;
import com.spring.boot.social.models.friendship.Friendship;
import com.spring.boot.social.models.friendship.FriendStatus;
import com.spring.boot.social.models.friendship.FriendshipStatus;
import com.spring.boot.social.repositories.FriendShipStatusRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.FriendStatusService;
import com.spring.boot.social.services.FriendshipService;
import com.spring.boot.social.services.FriendshipStatusService;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class FriendshipStatusServiceImpl implements FriendshipStatusService {
    private final FriendShipStatusRepo friendshipStatusRepo;
    private final AccountService accountService;
    private final FriendStatusService friendStatusService;
    private final FriendshipService friendshipService;

    @Override
    @Transactional
    public FriendshipStatusDto createFriendShipStatus(Long friendId) {
        //create friendship
        FriendShipDto friendShipDto = friendshipService.createFriendShip(friendId);
        Friendship friendship = FriendShipMapper.INSTANCE.toFriendship(friendShipDto);
        //get pending status
        FriendStatusDto friendStatusDto = friendStatusService.getStatus(FriendStatusEnum.PENDING);
        FriendStatus status = FriendStatusMapper.INSTANCE.toFriendStatus(friendStatusDto);
        //create relation between friends with status
        FriendshipStatus friendshipStatus = new FriendshipStatus();
        friendshipStatus.setFriendship(friendship);
        friendshipStatus.setStatus(status);
        friendshipStatus = friendshipStatusRepo.save(friendshipStatus);
        return FriendshipStatusMapper.INSTANCE.toFriendshipStatusDto(friendshipStatus);
    }

    @Override
    public void removeFriendShip(Long friendId) {

    }

    @Override
    public void takeActionOnRequestFriendShip(Long friendId, String status) {

    }
}
