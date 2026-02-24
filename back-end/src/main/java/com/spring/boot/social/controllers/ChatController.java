package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.chat.ChatService;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import com.spring.boot.social.vm.chat.MessageRequestVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/chat")
@RestController
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PatchMapping
    public ResponseEntity<SuccessDto<MessageDto>> sendMessage(@Valid @RequestBody MessageRequestVm messageRequestVm) {
        return ResponseEntity.ok(new SuccessDto<>(chatService.sendMessage(messageRequestVm)));
    }

    @GetMapping
    public ResponseEntity<SuccessDto<ChatResponseVm>> getChat(@RequestParam(value = "chat_id") Long chatId) {
        return ResponseEntity.ok(new SuccessDto<>(chatService.getChatResponseVm(chatId)));
    }
}
