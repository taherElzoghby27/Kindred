package com.spring.boot.social.services.impl.friendship;

import com.spring.boot.social.dto.friendship.FriendShipDto;
import com.spring.boot.social.dto.friendship.FriendStatusDto;
import com.spring.boot.social.dto.friendship.FriendshipStatusDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.FriendShipMapper;
import com.spring.boot.social.mappers.FriendStatusMapper;
import com.spring.boot.social.mappers.FriendshipStatusMapper;
import com.spring.boot.social.entity.friendship.FriendStatus;
import com.spring.boot.social.entity.friendship.Friendship;
import com.spring.boot.social.entity.friendship.FriendshipStatus;
import com.spring.boot.social.entity.security.Account;
import com.spring.boot.social.repositories.FriendShipStatusRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.services.friendship.FriendStatusService;
import com.spring.boot.social.services.friendship.FriendshipService;
import com.spring.boot.social.services.friendship.FriendshipStatusService;
import com.spring.boot.social.utils.enums.ActivityType;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import com.spring.boot.social.vm.RequestActivityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendshipStatusServiceImpl implements FriendshipStatusService {
    private final FriendShipStatusRepo friendshipStatusRepo;
    private final FriendStatusService friendStatusService;
    private final FriendshipService friendshipService;
    private final AccountService accountService;
    private final ActivityService activityService;

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
        //add log
        activityService.logActivity(new RequestActivityVm("sent friend request to " + friendShipDto.getFriend().getUsername(), ActivityType.FRIENDSHIP_REQUEST_SENT));
        return FriendshipStatusMapper.INSTANCE.toFriendshipStatusDto(friendshipStatus);
    }

    @Transactional(readOnly = true)
    @Override
    public FriendshipStatusDto getFriendshipStatusByFriendId(Long friendId) {
        if (Objects.isNull(friendId)) {
            throw new BadRequestException("empty.account_id");
        }
        //get friendship
        FriendShipDto friendShipDto = friendshipService.getFriendShip(friendId);
        Optional<FriendshipStatus> result = friendshipStatusRepo.findByFriendshipId(friendShipDto.getId());
        if (result.isEmpty()) {
            throw new NotFoundResourceException("friendship.not.exist");
        }
        return FriendshipStatusMapper.INSTANCE.toFriendshipStatusDto(result.get());
    }

    @Override
    public FriendshipStatusDto getFriendshipStatusById(Long friendshipStatusId) {
        if (Objects.isNull(friendshipStatusId)) {
            throw new BadRequestException("id.must.be.not.null");
        }
        Optional<FriendshipStatus> result = friendshipStatusRepo.findById(friendshipStatusId);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("friendship.not.exist");
        }
        return FriendshipStatusMapper.INSTANCE.toFriendshipStatusDto(result.get());
    }

    @Transactional
    @Override
    public void removeFriendShipStatusByFriendId(Long friendId) {
        FriendshipStatusDto friendshipStatusDto = getFriendshipStatusByFriendId(friendId);
        //delete friendship with status
        friendshipStatusRepo.deleteFriendshipStatusById(friendshipStatusDto.getId());
        //delete friendship
        friendshipService.removeFriendShip(friendId);
    }

    @Override
    @Transactional
    public void removeFriendShipStatusById(Long id) {
        FriendshipStatusDto friendshipStatusDto = getFriendshipStatusById(id);
        //delete friendship with status
        friendshipStatusRepo.deleteFriendshipStatusById(friendshipStatusDto.getId());
        //delete friendship
        friendshipService.removeFriendShip(friendshipStatusDto.getFriendship().getFriend().getId());
    }

    @Override
    @Transactional
    public void updateFriendshipStatus(Long id, String status) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("id.must.be.not.null");
        }
        //get status
        FriendStatusDto friendStatusDto = friendStatusService.getStatus(FriendStatusEnum.valueOf(status));
        FriendStatus newStatusFriend = FriendStatusMapper.INSTANCE.toFriendStatus(friendStatusDto);
        //get friendshipStatus
        FriendshipStatusDto friendshipStatusDto = getFriendshipStatusById(id);
        FriendshipStatus statusFriendship = FriendshipStatusMapper.INSTANCE.toFriendshipStatus(friendshipStatusDto);
        if (status.equals(friendshipStatusDto.getStatus().getStatus().name())) {
            return;
        }
        switch (friendStatusDto.getStatus()) {
            case ACCEPTED:
            case BLOCKED:
                updateFriendshipStatusMethod(statusFriendship, newStatusFriend);
                break;
            case REJECTED:
                removeFriendShipStatusById(statusFriendship.getId());
                //add log
                activityService.logActivity(new RequestActivityVm("rejected friend request with " + statusFriendship.getFriendship().getAccount().getUsername(), ActivityType.FRIENDSHIP_REJECTED));
                break;
        }
    }

    @Override
    public List<FriendshipStatusDto> getFriendshipStatusByStatus(String status) {
        Account account = accountService.getCurrentAccount();
        //get status
        FriendStatusDto friendStatusDto = friendStatusService.getStatus(FriendStatusEnum.valueOf(status));
        List<FriendshipStatus> friendshipStatuses = friendshipStatusRepo.findAllByAccountIdAndStatus(
                account.getId(),
                friendStatusDto.getStatus()
        );
        if (friendshipStatuses.isEmpty()) {
            throw new NotFoundResourceException("no.friendships");
        }
        return friendshipStatuses.stream().map(FriendshipStatusMapper.INSTANCE::toFriendshipStatusDto).toList();
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected void updateFriendshipStatusMethod(FriendshipStatus statusFriendship, FriendStatus statusFriend) {
        statusFriendship.setStatus(statusFriend);
        friendshipStatusRepo.save(statusFriendship);
        //add log
        switch (statusFriend.getStatus()) {
            case ACCEPTED:
                activityService.logActivity(new RequestActivityVm("Accepted friend request with " + statusFriendship.getFriendship().getAccount().getUsername(), ActivityType.FRIENDSHIP_ACCEPTED));
                break;
            case BLOCKED:
                activityService.logActivity(new RequestActivityVm("blocked " + statusFriendship.getFriendship().getAccount().getUsername(), ActivityType.FRIENDSHIP_BLOCKED));
                break;
        }
    }
}
