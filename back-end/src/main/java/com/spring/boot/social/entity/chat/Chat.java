package com.spring.boot.social.entity.chat;

import com.spring.boot.social.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Chat extends BaseEntity<String> {
    private LocalDateTime lastMessageAt;
    @OneToMany(mappedBy = "chat")
    private List<ChatParticipant> chatParticipants = new ArrayList<>();
    @OneToMany(mappedBy = "chat")
    private List<Message> messages = new ArrayList<>();
}
