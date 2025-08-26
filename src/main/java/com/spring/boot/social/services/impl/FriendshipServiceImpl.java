package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.models.Friendship;
import com.spring.boot.social.models.FriendshipStatus;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.FriendShipRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.FriendshipService;
import com.spring.boot.social.utils.SecurityUtils;
import com.spring.boot.social.utils.enums.FriendshipEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendShipRepo friendShipRepo;
    private final AccountService accountService;

    @Override
    @Transactional
    public void createFriendShip(Long friendId) {
        //current account
        Account account = getCurrentAccount();
        if (Objects.isNull(friendId)) {
            throw new BadRequestException("empty.account_id");
        }
        //get friend
        Account friend = getAccount(friendId);
        Friendship friendship = friendShipRepo.findFriendshipBetweenAccounts(account.getId(), friend.getId());
        if (Objects.nonNull(friendship)) {
            throw new NotFoundResourceException("friendship.already.exist");
        }
        //add pending status
        FriendshipStatus status = new FriendshipStatus();
        status.setStatus(FriendshipEnum.PENDING);
        //init friendship
        friendship = new Friendship();
        friendship.setAccount(account);
        friendship.setFriend(friend);
        friendship.setStatus(status);
        friendShipRepo.save(friendship);
    }

    private Account getCurrentAccount() {
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        return getAccount(accountDto.getId());
    }

    private Account getAccount(Long accountId) {
        AccountDto accountDto = accountService.getAccountById(accountId);
        return AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
    }

    @Override
    public void removeFriendShip(Long friendId) {

    }
}
