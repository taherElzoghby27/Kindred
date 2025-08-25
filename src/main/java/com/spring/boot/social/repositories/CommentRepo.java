package com.spring.boot.social.repositories;

import com.spring.boot.social.models.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CommentRepo extends JpaRepository<Comment, Long> {
    Optional<Comment> findByIdAndPostId(Long id, Long postId);

    Optional<List<Comment>> findByPostId(Long postId);
}
