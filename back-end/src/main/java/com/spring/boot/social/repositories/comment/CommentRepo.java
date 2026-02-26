package com.spring.boot.social.repositories.comment;

import com.spring.boot.social.entity.comment.Comment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndPostId(Long id, Long postId);

    Optional<Comment> findByIdAndAccountId(Long id, Long accountId);

    Page<Comment> findByPostId(Long postId, Pageable pageable);

    @Modifying
    @Query(value = "delete from Comment c where c.id=:id")
    void deleteByCommentId(Long id);

    @Modifying
    @Query("UPDATE Comment c SET c.reactionsCount=COALESCE(c.reactionsCount,0)+1 WHERE c.id=:commentId")
    void increaseReactionCount(@Param("commentId") Long commentId);

    @Modifying
    @Query("UPDATE Comment c SET c.reactionsCount=GREATEST(COALESCE(c.reactionsCount,0)-1,0) WHERE c.id=:commentId")
    void decreaseReactionCount(@Param("commentId") Long commentId);
}
