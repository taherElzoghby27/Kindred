package com.spring.boot.social.services;

import com.spring.boot.social.dto.CommentDto;
import com.spring.boot.social.vm.CommentRequestVm;
import com.spring.boot.social.vm.CommentResponseVm;

import java.util.List;

public interface CommentService {
    CommentResponseVm createComment(CommentRequestVm commentRequestVm);

    CommentResponseVm updateComment(CommentRequestVm commentRequestVm);

    void deleteComment(Long commentId);

    List<CommentResponseVm> getCommentsByPostId(Long postId);

    CommentDto getCommentByIdBasedOnAccount(Long commentId, Long postId);

    CommentResponseVm getCommentByIdBasedOnAccount(Long commentId);
}
