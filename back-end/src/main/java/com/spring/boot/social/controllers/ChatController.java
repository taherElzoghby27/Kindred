package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.chat.ChatService;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import com.spring.boot.social.vm.chat.MessageRequestVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    //@MessageMapping("/message")
    @PatchMapping
    public ResponseEntity<SuccessDto<MessageDto>> sendMessage(@Valid @RequestBody MessageRequestVm messageRequestVm) {
        MessageDto result = chatService.sendMessage(messageRequestVm);
        if (result.getAccount() != null && result.getAccount().getUsername() != null) {
            // Notify Sender
            simpMessagingTemplate.convertAndSendToUser(result.getAccount().getUsername(), "/listener/chat", result);

            // Notify Receiver
            if (result.getReceiver() != null) {
                simpMessagingTemplate.convertAndSendToUser(result.getReceiver().getUsername(), "/listener/chat", result);
            }
        }
        return ResponseEntity.ok(new SuccessDto<>(result));
    }

    @GetMapping
    public ResponseEntity<SuccessDto<ChatResponseVm>> getChat(@RequestParam(value = "chat_id") Long chatId) {
        return ResponseEntity.ok(new SuccessDto<>(chatService.getChat(chatId)));
    }
}
