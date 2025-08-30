package com.spring.boot.social.repositories;

import com.spring.boot.social.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findAllByAccountIdOrderByCreatedByAsc(Pageable pageable, Long accountId);

    Page<Post> findAllByOrderByCreatedByAsc(Pageable pageable);

    Post findPostsByIdAndAccountId(Long id, Long accountId);

    @Modifying
    @Query("UPDATE Post p SET p.reactionsCount = COALESCE(p.reactionsCount, 0) + 1 WHERE p.id = :postId")
    void incrementReactionCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.reactionsCount = GREATEST(COALESCE(p.reactionsCount, 0) - 1, 0) WHERE p.id = :postId")
    void decrementReactionCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.commentsCount = COALESCE(p.commentsCount, 0) + 1 WHERE p.id = :postId")
    void incrementCommentsCount(@Param("postId") Long postId);

    @Modifying
    @Query("UPDATE Post p SET p.commentsCount = GREATEST(COALESCE(p.commentsCount, 0) - 1, 0) WHERE p.id = :postId")
    void decrementCommentsCount(@Param("postId") Long postId);

}
