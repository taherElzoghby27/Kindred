package com.spring.boot.social.vm.chat;

import com.spring.boot.social.dto.chat.ChatParticipantDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatResponseVm {
    private Long id;
    private LocalDateTime lastMessageAt;
    private List<ChatParticipantDto> chatParticipants = new ArrayList<>();
    private List<MessageResponseVm> messages = new ArrayList<>();
}
