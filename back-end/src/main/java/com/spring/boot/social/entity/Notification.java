package com.spring.boot.social.entity;

import com.spring.boot.social.utils.enums.NotificationType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Notification extends BaseEntity<String> {
    private Long senderId;
    private String senderName;

    @Enumerated(EnumType.STRING)
    private NotificationType type;

    private Long referenceId;
    private boolean isRead;

    private LocalDateTime createdAt = LocalDateTime.now();
    @ManyToOne(fetch = FetchType.LAZY)
    private Account recipient;
}
