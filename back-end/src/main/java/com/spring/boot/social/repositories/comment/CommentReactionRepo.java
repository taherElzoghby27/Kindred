package com.spring.boot.social.repositories.comment;

import com.spring.boot.social.entity.comment.CommentReactionAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentReactionRepo extends JpaRepository<CommentReactionAccount, Long> {
    Optional<CommentReactionAccount> findByAccountIdAndCommentId(Long accountId, Long commentId);
}
