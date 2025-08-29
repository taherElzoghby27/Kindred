package com.spring.boot.social.repositories;

import com.spring.boot.social.models.PostReactionAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionPostRepo extends JpaRepository<PostReactionAccount, Long> {
}
