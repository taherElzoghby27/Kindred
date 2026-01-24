package com.spring.boot.social.config.security;

import com.spring.boot.social.config.security.TokenHandler;
import com.spring.boot.social.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final TokenHandler tokenHandler;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        
        if (accessor != null && StompCommand.CONNECT.equals(accessor.getCommand())) {
            String authHeader = accessor.getFirstNativeHeader("Authorization");
            
            if (authHeader != null && authHeader.startsWith("Bearer ")) {
                String token = authHeader.substring(7);
                try {
                    AccountDto accountDto = tokenHandler.validateToken(token);
                    if (accountDto != null) {
                        Authentication authentication = new UsernamePasswordAuthenticationToken(
                                accountDto,
                                accountDto.getPassword(),
                                new ArrayList<>()
                        );
                        accessor.setUser(authentication);
                        System.out.println("WebSocket authenticated user: " + accountDto.getUsername());
                    }
                } catch (Exception e) {
                    System.err.println("WebSocket authentication failed: " + e.getMessage());
                }
            }
        }
        
        return message;
    }
}
