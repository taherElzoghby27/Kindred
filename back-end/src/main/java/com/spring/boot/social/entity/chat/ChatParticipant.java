package com.spring.boot.social.entity.chat;

import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"chat_id", "account_id"}))
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatParticipant extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @OneToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
