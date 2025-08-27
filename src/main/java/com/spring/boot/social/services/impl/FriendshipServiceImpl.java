package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.FriendShipDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.mappers.FriendShipMapper;
import com.spring.boot.social.models.friendship.Friendship;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.FriendShipRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.FriendshipService;
import com.spring.boot.social.utils.SecurityUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendShipRepo friendshipRepo;
    private final AccountService accountService;

    @Override
    public FriendShipDto createFriendShip(Long friendId) {
        //current account
        Account account = getCurrentAccount();
        if (Objects.isNull(friendId)) {
            throw new BadRequestException("empty.account_id");
        }
        //get friend
        Account friend = getAccount(friendId);
        Friendship friendship = friendshipRepo.findFriendshipBetweenAccounts(account.getId(), friend.getId());
        if (Objects.nonNull(friendship)) {
            throw new NotFoundResourceException("friendship.already.exist");
        }
        //create friendship without status
        friendship = new Friendship();
        friendship.setAccount(account);
        friendship.setFriend(friend);
        //set status
        friendship = friendshipRepo.save(friendship);
        return FriendShipMapper.INSTANCE.toFriendShipDto(friendship);
    }

    @Override
    @Transactional(readOnly = true)
    public FriendShipDto getFriendShip(Long friendId) {
        //current account
        Account account = getCurrentAccount();
        if (Objects.isNull(friendId)) {
            throw new BadRequestException("empty.account_id");
        }
        //get friend
        Account friend = getAccount(friendId);
        Friendship friendship = friendshipRepo.findFriendshipBetweenAccounts(account.getId(), friend.getId());
        return FriendShipMapper.INSTANCE.toFriendShipDto(friendship);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeFriendShip(Long friendId) {
        FriendShipDto friendShipDto = getFriendShip(friendId);
        friendshipRepo.deleteFriendShipById(friendShipDto.getId());
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
