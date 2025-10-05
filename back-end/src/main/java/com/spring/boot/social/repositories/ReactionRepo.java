package com.spring.boot.social.repositories;

import com.spring.boot.social.entity.Reaction;
import com.spring.boot.social.utils.enums.ReactionType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReactionRepo extends JpaRepository<Reaction, Integer> {
    Optional<Reaction> findReactionByReactionType(ReactionType reactionType);
}
