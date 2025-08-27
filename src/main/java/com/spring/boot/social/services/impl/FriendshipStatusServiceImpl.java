package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.FriendShipDto;
import com.spring.boot.social.dto.FriendshipStatusDto;
import com.spring.boot.social.dto.FriendStatusDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.mappers.FriendShipMapper;
import com.spring.boot.social.mappers.FriendStatusMapper;
import com.spring.boot.social.mappers.FriendshipStatusMapper;
import com.spring.boot.social.models.friendship.Friendship;
import com.spring.boot.social.models.friendship.FriendStatus;
import com.spring.boot.social.models.friendship.FriendshipStatus;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.FriendShipStatusRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.FriendStatusService;
import com.spring.boot.social.services.FriendshipService;
import com.spring.boot.social.services.FriendshipStatusService;
import com.spring.boot.social.utils.SecurityUtils;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipStatusServiceImpl implements FriendshipStatusService {
    private final FriendShipStatusRepo friendshipStatusRepo;
    private final FriendStatusService friendStatusService;
    private final FriendshipService friendshipService;
    private final AccountService accountService;

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

    @Transactional(readOnly = true)
    @Override
    public FriendshipStatusDto getFriendshipStatus(Long friendId) {
        if (Objects.isNull(friendId)) {
            throw new BadRequestException("empty.account_id");
        }
        //get friendship
        FriendShipDto friendShipDto = friendshipService.getFriendShip(friendId);
        Optional<FriendshipStatus> result = friendshipStatusRepo.findById(friendShipDto.getId());
        if (result.isEmpty()) {
            throw new NotFoundResourceException("friendship.not.exist");
        }
        return FriendshipStatusMapper.INSTANCE.toFriendshipStatusDto(result.get());
    }

    @Transactional
    @Override
    public void removeFriendShipStatus(Long friendId) {
        FriendshipStatusDto friendshipStatusDto = getFriendshipStatus(friendId);
        //delete friendship with status
        friendshipStatusRepo.deleteFriendshipStatusById(friendshipStatusDto.getId());
        //delete friendship
        friendshipService.removeFriendShip(friendId);
    }

    @Override
    public void takeActionOnRequestFriendShip(Long friendId, String status) {

    }

    private Account getCurrentAccount() {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        return getAccount(accountDto.getId());
    }

    private Account getAccount(Long accountId) {
        AccountDto accountDto = accountService.getAccountById(accountId);
        return AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
    }
}
