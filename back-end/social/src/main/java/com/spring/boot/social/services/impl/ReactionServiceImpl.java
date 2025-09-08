package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.ReactionMapper;
import com.spring.boot.social.models.Reaction;
import com.spring.boot.social.repositories.ReactionRepo;
import com.spring.boot.social.services.ReactionService;
import com.spring.boot.social.utils.enums.ReactionType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepo reactionRepo;

    @Override
    public ReactionDto getReaction(ReactionType type) {
        if (Objects.isNull(type)) {
            throw new BadRequestException("type.not.null");
        }
        Optional<Reaction> result = reactionRepo.findReactionByReactionType(type);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("reaction.not.found");
        }
        return ReactionMapper.INSTANCE.toReactionDto(result.get());
    }
}
