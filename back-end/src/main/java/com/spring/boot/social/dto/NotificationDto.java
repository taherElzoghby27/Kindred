package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.utils.enums.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotificationDto {
    Long id;
    @JsonProperty("sender_id")
    private Long senderId;
    @JsonProperty("sender_name")
    private String senderName;

    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @JsonProperty("reference_id")
    private Long referenceId;
    @JsonProperty("is_read")
    private boolean isRead;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
}
