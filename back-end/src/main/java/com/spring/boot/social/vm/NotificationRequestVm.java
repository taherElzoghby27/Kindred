package com.spring.boot.social.vm;
import com.spring.boot.social.utils.enums.NotificationType;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class NotificationRequestVm {
    @NotNull(message = "sender_id.not_null")
    private Long senderId;
    @NotNull(message = "sender_name.not_null")
    private String senderName;
    @NotNull(message = "type.not_null")
    @Enumerated(EnumType.STRING)
    private NotificationType type;
    @NotNull(message = "reference_id.not_null")
    private Long referenceId;
    @NotNull(message = "recipient_id.not_null")
    private Long recipientId;
}
