package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.CommentDto;
import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.CommentMapper;
import com.spring.boot.social.mappers.PostMapper;
import com.spring.boot.social.models.Comment;
import com.spring.boot.social.models.Post;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.CommentRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.services.CommentService;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.utils.enums.ActivityType;
import com.spring.boot.social.vm.CommentRequestVm;
import com.spring.boot.social.vm.CommentResponseVm;
import com.spring.boot.social.vm.RequestActivityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepo commentRepo;
    private final PostService postService;
    private final AccountService accountService;
    @Lazy
    private final ActivityService activityService;

    @Override
    @Transactional
    public CommentResponseVm createComment(CommentRequestVm commentRequestVm) {
        if (Objects.nonNull(commentRequestVm.getId())) {
            throw new BadRequestException("id.comment.null");
        }
        Account account = accountService.getCurrentAccount();
        PostDto postDto = postService.getPost(commentRequestVm.getPostId());
        Post post = PostMapper.POST_INSTANCE.toPost(postDto);
        Comment comment = CommentMapper.COMMENT_MAPPER.toComment(commentRequestVm);
        comment.setPost(post);
        comment.setAccount(account);
        comment = commentRepo.save(comment);
        //increment commentsCount num in post
        postService.incrementCommentCount(post.getId());
        //add log
        activityService.logActivity(
                new RequestActivityVm(
                        "Created comment " + comment.getContent() + " on post " + post.getAccount().getUsername(),
                        ActivityType.COMMENT_CREATED
                )
        );
        return CommentMapper.COMMENT_MAPPER.toCommentResponseVm(comment);
    }

    @Override
    public CommentResponseVm updateComment(CommentRequestVm commentRequestVm) {
        if (Objects.isNull(commentRequestVm.getId())) {
            throw new BadRequestException("id.comment.not_null");
        }
        CommentDto oldCommentDto = getCommentByIdBasedOnAccount(commentRequestVm.getId(), commentRequestVm.getPostId());
        if (oldCommentDto.getContent().equals(commentRequestVm.getContent())) {
            throw new BadRequestException("no.changes");
        }
        oldCommentDto.setContent(commentRequestVm.getContent());
        Post post = PostMapper.POST_INSTANCE.toPost(oldCommentDto.getPost());
        Comment comment = CommentMapper.COMMENT_MAPPER.toComment(commentRequestVm);
        comment.setPost(post);
        comment = commentRepo.save(comment);
        return CommentMapper.COMMENT_MAPPER.toCommentResponseVm(comment);
    }

    @Transactional
    @Override
    public void deleteComment(Long commentId) {
        if (Objects.isNull(commentId)) {
            throw new BadRequestException("id.comment.not_null");
        }
        CommentResponseVm commentResponseVm = getCommentByIdBasedOnAccount(commentId);
        commentRepo.deleteByCommentId(commentId);
        //decrement commentsCount num in post
        postService.decrementCommentCount(commentResponseVm.getPostId());
    }

    @Override
    public List<CommentResponseVm> getCommentsByPostId(Long postId) {
        if (Objects.isNull(postId)) {
            throw new BadRequestException("post_id.comment.not_null");
        }
        Optional<List<Comment>> result = commentRepo.findByPostId(postId);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("comments.not.found");
        }
        return result.get().stream().map(CommentMapper.COMMENT_MAPPER::toCommentResponseVm).toList();
    }

    @Override
    public CommentDto getCommentByIdBasedOnAccount(Long commentId, Long postId) {
        if (Objects.isNull(commentId)) {
            throw new BadRequestException("id.comment.not_null");
        }
        if (Objects.isNull(postId)) {
            throw new BadRequestException("post_id.comment.not_null");
        }
        Optional<Comment> result = commentRepo.findByIdAndPostId(commentId, postId);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("comment.not.found");
        }
        return CommentMapper.COMMENT_MAPPER.toCommentDto(result.get());
    }

    @Override
    public CommentResponseVm getCommentByIdBasedOnAccount(Long commentId) {
        if (Objects.isNull(commentId)) {
            throw new BadRequestException("id.comment.not_null");
        }
        Account account = accountService.getCurrentAccount();
        Optional<Comment> result = commentRepo.findByIdAndAccountId(commentId, account.getId());
        if (result.isEmpty()) {
            throw new NotFoundResourceException("comment.not.found");
        }
        return CommentMapper.COMMENT_MAPPER.toCommentResponseVm(result.get());
    }
}
