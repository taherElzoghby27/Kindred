package com.spring.boot.social.repositories;

import com.spring.boot.social.models.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepo extends JpaRepository<Post, Long> {
    Page<Post> findAllByOrderByCreatedByAsc(Pageable pageable);

    Post findPostsByIdAndAccountId(Long id, Long accountId);
}
