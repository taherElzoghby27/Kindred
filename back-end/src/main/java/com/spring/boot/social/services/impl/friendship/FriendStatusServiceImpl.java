package com.spring.boot.social.services.impl.friendship;

import com.spring.boot.social.dto.friendship.FriendStatusDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.mappers.FriendStatusMapper;
import com.spring.boot.social.entity.friendship.FriendStatus;
import com.spring.boot.social.repositories.FriendStatusRepo;
import com.spring.boot.social.services.friendship.FriendStatusService;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class FriendStatusServiceImpl implements FriendStatusService {
    private final FriendStatusRepo friendStatusRepo;

    @Override
    public FriendStatusDto getStatus(FriendStatusEnum status) {
        if (Objects.isNull(status)) {
            throw new BadRequestException("status.must.be.not_null");
        }
        Optional<FriendStatus> result = friendStatusRepo.findByStatus(status);
        if (result.isEmpty()) {
            throw new BadRequestException("status.not.found");
        }
        return FriendStatusMapper.INSTANCE.toFriendStatusDto(result.get());
    }
}
