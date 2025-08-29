package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.PostMapper;
import com.spring.boot.social.mappers.ReactionMapper;
import com.spring.boot.social.models.Post;
import com.spring.boot.social.models.PostReactionAccount;
import com.spring.boot.social.models.Reaction;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.ReactionPostRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.services.ReactionPostService;
import com.spring.boot.social.services.ReactionService;
import com.spring.boot.social.vm.ReactionRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionPostServiceImpl implements ReactionPostService {
    private final ReactionPostRepo reactionPostRepo;
    private final ReactionService reactionService;
    private final AccountService accountService;
    private final PostService postService;

    @Override
    public void reactionRequest(ReactionRequestVm reactionRequestVm) {
        //get account
        Account account = accountService.getCurrentAccount();
        //get post
        PostDto postDto = postService.getPost(reactionRequestVm.getPostId());
        Post post = PostMapper.POST_INSTANCE.toPost(postDto);
        //get react
        ReactionDto reactionDto = reactionService.getReaction(reactionRequestVm.getReactionType());
        Reaction reaction = ReactionMapper.INSTANCE.toReaction(reactionDto);
        Optional<PostReactionAccount> result = reactionPostRepo.findByPostIdAndAccountId(post.getId(), account.getId());
        PostReactionAccount postReactionAccount;
        //create reaction with post if not exist else update reaction
        postReactionAccount = result.map(reactionAccount -> updateReaction(reactionAccount, reaction)).orElseGet(() -> createNewReactionWithPost(account, post, reaction));
        reactionPostRepo.save(postReactionAccount);
    }

    private static PostReactionAccount updateReaction(PostReactionAccount postReactionAccount, Reaction reaction) {
        postReactionAccount.setReaction(reaction);
        return postReactionAccount;
    }

    private static PostReactionAccount createNewReactionWithPost(Account account, Post post, Reaction reaction) {
        PostReactionAccount postReactionAccount = new PostReactionAccount();
        postReactionAccount.setAccount(account);
        postReactionAccount.setPost(post);
        postReactionAccount.setReaction(reaction);
        return postReactionAccount;
    }

    @Override
    public void removeReaction(ReactionRequestVm reactionRequestVm) {
        if (Objects.isNull(reactionRequestVm.getPostId())) {
            throw new BadRequestException("post_id.comment.not_null");
        }
        //get account
        Account account = accountService.getCurrentAccount();

        Optional<PostReactionAccount> result = reactionPostRepo.findByPostIdAndAccountId(reactionRequestVm.getPostId(), account.getId());
        result.map(
                rPA -> reactionPostRepo.deleteByPostReactionAccountId(rPA.getId())
        ).orElseThrow(() -> new NotFoundResourceException("reaction.not.found"));
    }

}
