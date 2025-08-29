package com.spring.boot.social.repositories;

import com.spring.boot.social.models.Reaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReactionRepo extends JpaRepository<Reaction, Integer> {
}
