package com.spring.boot.social.services;

import com.spring.boot.social.vm.PostReactionAccountVm;
import com.spring.boot.social.vm.ReactionRequestVm;

public interface ReactionPostService {
    PostReactionAccountVm reactionRequest(ReactionRequestVm reactionRequestVm);

    void removeReaction(Long postId);
}
