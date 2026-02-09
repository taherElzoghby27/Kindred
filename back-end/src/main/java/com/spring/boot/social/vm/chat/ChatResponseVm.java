package com.spring.boot.social.vm.chat;

import com.spring.boot.social.dto.chat.ChatParticipantDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ChatResponseVm {
    private Long id;
    private List<ChatParticipantDto> chatParticipants = new ArrayList<>();
    private List<MessageResponseVm> messages = new ArrayList<>();
}
