package com.spring.boot.social.repositories.chat;

import com.spring.boot.social.entity.chat.ChatParticipant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatParticipantRepo extends JpaRepository<ChatParticipant, Long> {
    @Query("select count(*) from Chat c join ChatParticipant p on c.id=p.chat.id and c.id=:chat_id")
    Long getParticipantsCount(@Param("chat_id") Long chatId);
}
