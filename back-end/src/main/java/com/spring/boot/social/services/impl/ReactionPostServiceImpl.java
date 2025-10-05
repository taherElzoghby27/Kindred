package com.spring.boot.social.services.impl;
import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.PostMapper;
import com.spring.boot.social.mappers.ReactionMapper;
import com.spring.boot.social.entity.Post;
import com.spring.boot.social.entity.PostReactionAccount;
import com.spring.boot.social.entity.Reaction;
import com.spring.boot.social.entity.security.Account;
import com.spring.boot.social.repositories.ReactionPostRepo;
import com.spring.boot.social.services.*;
import com.spring.boot.social.utils.enums.ActivityType;
import com.spring.boot.social.vm.ReactionRequestVm;
import com.spring.boot.social.vm.RequestActivityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionPostServiceImpl implements ReactionPostService {
    private final ReactionPostRepo reactionPostRepo;
    private final ReactionService reactionService;
    private final AccountService accountService;
    private final PostService postService;
    private final ActivityService activityService;

    @Transactional
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
        postReactionAccount = result.map(reactionAccount -> updateReaction(reactionAccount, reaction))
                .orElseGet(() -> createNewReactionWithPost(account, post, reaction));
        reactionPostRepo.save(postReactionAccount);
        //add liked in post
        //postService.makeItLiked(post.getId());
        //add log
        activityService.logActivity(new RequestActivityVm("react on " + post.getContent(), ActivityType.REACTION_ADDED));
    }

    protected PostReactionAccount updateReaction(PostReactionAccount postReactionAccount, Reaction reaction) {
        postReactionAccount.setReaction(reaction);
        return postReactionAccount;
    }

    @Transactional(propagation = Propagation.MANDATORY)
    protected PostReactionAccount createNewReactionWithPost(Account account, Post post, Reaction reaction) {
        PostReactionAccount postReactionAccount = new PostReactionAccount();
        postReactionAccount.setAccount(account);
        postReactionAccount.setReaction(reaction);
        // atomic increment
        postService.incrementReactionCount(post.getId());
        postReactionAccount.setPost(post);
        return postReactionAccount;
    }

    @Transactional
    @Override
    public void removeReaction(ReactionRequestVm reactionRequestVm) {
        if (Objects.isNull(reactionRequestVm.getPostId())) {
            throw new BadRequestException("post_id.comment.not_null");
        }
        //get account
        Account account = accountService.getCurrentAccount();

        Optional<PostReactionAccount> result = reactionPostRepo.findByPostIdAndAccountId(reactionRequestVm.getPostId(), account.getId());
        result.map(rPA -> removeProcess(reactionRequestVm, rPA)).orElseThrow(() -> new NotFoundResourceException("reaction.not.found"));
    }



    @Transactional(propagation = Propagation.MANDATORY)
    protected Optional<Object> removeProcess(ReactionRequestVm reactionRequestVm, PostReactionAccount rPA) {
        reactionPostRepo.deleteByPostReactionAccountId(rPA.getId());
        postService.decrementReactionCount(reactionRequestVm.getPostId());
        //add liked in post
        //postService.makeItDisliked(reactionRequestVm.getPostId());
        return Optional.empty();
    }
}
