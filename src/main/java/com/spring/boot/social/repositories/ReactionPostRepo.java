package com.spring.boot.social.repositories;

import com.spring.boot.social.models.PostReactionAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionPostRepo extends JpaRepository<PostReactionAccount, Long> {
    Optional<PostReactionAccount> findByPostIdAndAccountId(Long postId, Long accountId);

    @Modifying
    @Query(value = "delete from PostReactionAccount p where p.id=:id")
    Optional<Object> deleteByPostReactionAccountId(Long id);
}
