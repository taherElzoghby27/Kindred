package com.spring.boot.social.services;

import com.spring.boot.social.dto.ReactionDto;

public interface ReactionService {
    ReactionDto getReaction(String type);
}
