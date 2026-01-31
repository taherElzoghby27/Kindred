package com.spring.boot.social.repositories.chat;

import com.spring.boot.social.entity.chat.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatParticipantRepo extends JpaRepository<ChatParticipant, Long> {
}
