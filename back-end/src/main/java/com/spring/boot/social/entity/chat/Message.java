package com.spring.boot.social.entity.chat;

import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Message extends BaseEntity<String> {
    @Column(nullable = false)
    private String text;
    private boolean seen = false;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private Account account;
}
