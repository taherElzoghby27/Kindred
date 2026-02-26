package com.spring.boot.social.services.comment;

import com.spring.boot.social.vm.comment.ReactionCommentRequestVm;

public interface ReactionCommentService {
    void changeReaction(ReactionCommentRequestVm reactionRequestVm);
}
