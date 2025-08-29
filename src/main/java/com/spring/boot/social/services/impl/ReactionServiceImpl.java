package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.mappers.PostMapper;
import com.spring.boot.social.models.Post;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.ReactionRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.services.ReactionService;
import com.spring.boot.social.vm.ReactionRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ReactionServiceImpl implements ReactionService {
    private final ReactionRepo reactionRepo;
    private final AccountService accountService;
    private final PostService postService;

    @Override
    public ReactionDto createReaction(ReactionRequestVm reactionRequestVm) {
        if (Objects.isNull(reactionRequestVm.getPostId())) {
            throw new BadRequestException("post_id.comment.not_null");
        }
        //get account
        Account account = accountService.getCurrentAccount();
        //get post
        PostDto postDto = postService.getPost(reactionRequestVm.getPostId());
        Post post = PostMapper.POST_INSTANCE.toPost(postDto);
        //get react


        return null;
    }

    @Override
    public ReactionDto removeReaction(ReactionRequestVm reactionRequestVm) {
        return null;
    }

    @Override
    public ReactionDto updateReaction(ReactionRequestVm reactionRequestVm) {
        return null;
    }
}
