package com.spring.boot.social.services.comment;

import com.spring.boot.social.dto.CommentDto;
import com.spring.boot.social.entity.comment.Comment;
import com.spring.boot.social.vm.comment.CommentRequestVm;
import com.spring.boot.social.vm.comment.CommentResponseVm;
import com.spring.boot.social.vm.GeneralResponseVm;

public interface CommentService {
    CommentResponseVm createComment(CommentRequestVm commentRequestVm);

    CommentResponseVm updateComment(CommentRequestVm commentRequestVm);

    void deleteCommentBasedOnAccount(Long commentId);

    GeneralResponseVm<CommentResponseVm> getCommentsByPostId(Long postId, int page, int size);

    CommentDto getCommentDtoByIdAndPostId(Long commentId, Long postId);

    CommentResponseVm getCommentResponseVmByIdBasedOnAccount(Long commentId);

    Comment getCommentById(Long id);

    void increaseReactionsCount(Long commentId);

    void decreaseReactionsCount(Long commentId);
}
