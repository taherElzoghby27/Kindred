package com.spring.boot.social.repositories;

import com.spring.boot.social.entity.PostReactionAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReactionPostRepo extends JpaRepository<PostReactionAccount, Long> {
    Optional<PostReactionAccount> findByPostIdAndAccountId(Long postId, Long accountId);

    @Query("select pra.post.id from PostReactionAccount pra where pra.account.id=:accountId")
    List<Long> findPostIdsLikedByAccount(@Param("accountId") Long accountId);

    @Modifying
    @Query(value = "delete from PostReactionAccount p where p.id=:id")
    Optional<Object> deleteByPostReactionAccountId(Long id);
}
