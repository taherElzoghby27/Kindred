package com.spring.boot.social.services.impl.friendship;

import com.spring.boot.social.dto.friendship.FriendShipDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.FriendShipMapper;
import com.spring.boot.social.entity.friendship.Friendship;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.repositories.FriendShipRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.friendship.FriendshipService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipServiceImpl implements FriendshipService {
    private final FriendShipRepo friendshipRepo;
    private final AccountService accountService;

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public FriendShipDto createFriendShip(Long friendId) {
        //current account
        Account account = accountService.getCurrentAccount();
        if (Objects.isNull(friendId)) {
            throw new BadRequestException("empty.account_id");
        }
        if (friendId.equals(account.getId())) {
            throw new BadRequestException("two.accounts.must.be.diff");
        }
        //get friend
        Account friend = accountService.getAccount(friendId);
        Optional<Friendship> result = friendshipRepo.findFriendshipBetweenAccounts(account.getId(), friend.getId());
        if (result.isPresent()) {
            throw new NotFoundResourceException("friendship.already.exist");
        }
        //create friendship without status
        Friendship friendship = new Friendship();
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
        Account account = accountService.getCurrentAccount();
        if (Objects.isNull(friendId)) {
            throw new BadRequestException("empty.account_id");
        }
        //get friend
        Account friend = accountService.getAccount(friendId);
        Optional<Friendship> result = friendshipRepo.findFriendshipBetweenAccounts(account.getId(), friend.getId());
        if (result.isEmpty()) {
            throw new NotFoundResourceException("friendship.not.exist");
        }
        return FriendShipMapper.INSTANCE.toFriendShipDto(result.get());
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void removeFriendShip(Long friendId) {
        FriendShipDto friendShipDto = getFriendShip(friendId);
        friendshipRepo.deleteFriendShipById(friendShipDto.getId());
    }
}
