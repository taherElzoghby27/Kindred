package com.spring.boot.social.services;

import com.spring.boot.social.dto.CommentDto;
import com.spring.boot.social.vm.CommentRequestVm;

import java.util.List;

public interface CommentService {
    CommentDto createComment(CommentRequestVm commentRequestVm);

    CommentDto updateComment(CommentRequestVm commentRequestVm);

    void deleteComment(Long commentId);

    List<CommentDto> getCommentsByPostId(Long postId);

    CommentDto getCommentById(Long commentId, Long postId);

    CommentDto getCommentById(Long commentId);
}
