package com.spring.boot.social.entity.chat;

import com.spring.boot.social.entity.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class Chat extends BaseEntity<String> {
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<ChatParticipant> chatParticipants = new ArrayList<>();
    @OneToMany(mappedBy = "chat", cascade = CascadeType.ALL)
    private List<Message> messages = new ArrayList<>();
}
