package com.spring.boot.social.services.comment.impl;

import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.Reaction;
import com.spring.boot.social.entity.comment.Comment;
import com.spring.boot.social.entity.comment.CommentReactionAccount;
import com.spring.boot.social.repositories.comment.CommentReactionRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.ReactionService;
import com.spring.boot.social.services.comment.CommentService;
import com.spring.boot.social.services.comment.ReactionCommentService;
import com.spring.boot.social.vm.comment.ReactionCommentRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReactionCommentServiceImpl implements ReactionCommentService {
    private final CommentService commentService;
    private final AccountService accountService;
    private final ReactionService reactionService;
    private final CommentReactionRepo commentReactionRepo;

    @Override
    @Transactional
    public void changeReaction(ReactionCommentRequestVm reactionRequestVm) {
        //my account
        Account currentAccount = accountService.getCurrentAccount();
        //my comment
        Comment comment = commentService.getCommentById(reactionRequestVm.getCommentId());
        //my reaction
        Reaction reaction = reactionService.getReaction(reactionRequestVm.getReactionType());
        Optional<CommentReactionAccount> result = commentReactionRepo.findByAccountIdAndCommentId(
                currentAccount.getId(),
                comment.getId()
        );
        handleChangeCommentReaction(result, reaction, comment, currentAccount);
    }

    private void handleChangeCommentReaction(Optional<CommentReactionAccount> result, Reaction reaction, Comment comment, Account currentAccount) {
        if (result.isPresent()) {
            CommentReactionAccount commentReactionAccount = result.get();
            if (commentReactionAccount.getReaction().getReactionType().equals(reaction.getReactionType())) {
                deleteReactionComment(commentReactionAccount.getId());
            } else {
                updateReactionComment(commentReactionAccount, reaction);
            }
        } else {
            addReactionComment(comment, reaction, currentAccount);
        }
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void addReactionComment(Comment comment, Reaction reaction, Account currentAccount) {
        CommentReactionAccount commentReactionAccount = new CommentReactionAccount();
        commentReactionAccount.setComment(comment);
        commentReactionAccount.setAccount(currentAccount);
        commentReactionAccount.setReaction(reaction);
        commentReactionRepo.save(commentReactionAccount);
        commentService.increaseReactionsCount(comment.getId());
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void updateReactionComment(CommentReactionAccount commentReactionAccount, Reaction reaction) {
        commentReactionAccount.setReaction(reaction);
        commentReactionRepo.save(commentReactionAccount);
    }

    @Transactional(propagation = Propagation.MANDATORY)
    private void deleteReactionComment(Long id) {
        commentReactionRepo.deleteById(id);
        commentService.decreaseReactionsCount(id);
    }
}
