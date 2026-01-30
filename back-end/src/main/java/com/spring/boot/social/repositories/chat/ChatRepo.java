package com.spring.boot.social.repositories.chat;

import com.spring.boot.social.entity.chat.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ChatRepo extends JpaRepository<Chat, Long> {
    //Optional<Chat> findById(Long id);
    //Chat findByIdOrderByCreatedAtAsc(Long id);
}
