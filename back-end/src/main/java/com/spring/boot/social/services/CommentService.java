package com.spring.boot.social.services;

import com.spring.boot.social.dto.CommentDto;
import com.spring.boot.social.vm.CommentRequestVm;
import com.spring.boot.social.vm.CommentResponseVm;
import com.spring.boot.social.vm.GeneralResponseVm;

public interface CommentService {
    CommentResponseVm createComment(CommentRequestVm commentRequestVm);

    CommentResponseVm updateComment(CommentRequestVm commentRequestVm);

    void deleteCommentBasedOnAccount(Long commentId);

    GeneralResponseVm<CommentResponseVm> getCommentsByPostId(Long postId, int page, int size);

    CommentDto getCommentDtoByIdAndPostId(Long commentId, Long postId);

    CommentResponseVm getCommentResponseVmByIdBasedOnAccount(Long commentId);
}
