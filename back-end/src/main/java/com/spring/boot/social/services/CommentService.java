package com.spring.boot.social.services;

import com.spring.boot.social.dto.CommentDto;
import com.spring.boot.social.vm.CommentRequestVm;
import com.spring.boot.social.vm.CommentResponseVm;
import com.spring.boot.social.vm.GeneralResponseVm;

public interface CommentService {
    CommentResponseVm createComment(CommentRequestVm commentRequestVm);

    CommentResponseVm updateComment(CommentRequestVm commentRequestVm);

    void deleteComment(Long commentId);

    GeneralResponseVm<CommentResponseVm> getCommentsByPostId(Long postId, int page, int size);

    CommentDto getCommentByIdAndPostId(Long commentId, Long postId);

    CommentResponseVm getCommentByIdBasedOnAccount(Long commentId);
}
