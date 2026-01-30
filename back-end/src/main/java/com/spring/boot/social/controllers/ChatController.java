package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.services.chat.ChatService;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import com.spring.boot.social.vm.chat.MessageRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PatchMapping
    public ResponseEntity<MessageDto> sendMessage(@RequestBody MessageRequestVm messageRequestVm) {
        return ResponseEntity.ok(chatService.sendMessage(messageRequestVm));
    }

    @GetMapping
    public ResponseEntity<ChatResponseVm> getChat(@RequestParam(value = "chat_id") Long chatId) {
        return ResponseEntity.ok(chatService.getChat(chatId));
    }
}
