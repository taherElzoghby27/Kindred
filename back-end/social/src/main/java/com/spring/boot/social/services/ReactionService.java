package com.spring.boot.social.services;

import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.utils.enums.ReactionType;

public interface ReactionService {
    ReactionDto getReaction(ReactionType type);
}
