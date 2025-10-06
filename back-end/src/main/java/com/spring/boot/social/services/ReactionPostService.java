package com.spring.boot.social.services;

import com.spring.boot.social.vm.ReactionRequestVm;

public interface ReactionPostService {
    void reactionRequest(ReactionRequestVm reactionRequestVm);

    void removeReaction(Long postId);
}
