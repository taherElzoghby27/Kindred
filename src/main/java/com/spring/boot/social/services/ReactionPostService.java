package com.spring.boot.social.services;

import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.vm.ReactionRequestVm;

public interface ReactionPostService {
    ReactionDto createReaction(ReactionRequestVm reactionRequestVm);

    ReactionDto removeReaction(ReactionRequestVm reactionRequestVm);

    ReactionDto updateReaction(ReactionRequestVm reactionRequestVm);

}
