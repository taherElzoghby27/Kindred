package com.spring.boot.social.entity.chat;

import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
//@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"chat_id", "account_id"}))
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChatParticipant extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private Chat chat;
    @ManyToOne
    @JoinColumn(name = "account_id")
    private Account account;
}
