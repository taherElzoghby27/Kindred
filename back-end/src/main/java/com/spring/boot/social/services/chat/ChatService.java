package com.spring.boot.social.services.chat;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import com.spring.boot.social.vm.chat.MessageRequestVm;

public interface ChatService {
    ChatResponseVm getChat(Long chatId);

    MessageDto sendMessage(MessageRequestVm messageRequestVm);
}
