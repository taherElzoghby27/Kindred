package com.spring.boot.social.config.security;

import com.spring.boot.social.dto.AccountDto;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
@RequiredArgsConstructor
public class WebSocketAuthInterceptor implements ChannelInterceptor {
    private final TokenHandler tokenHandler;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            // Access authentication header(s) and invoke accessor.setUser(user)
            //get authorization header
            String auth = accessor.getFirstNativeHeader("Authorization");
            //get token
            if (auth != null && auth.startsWith("Bearer ")) {
                String token = auth.substring(7);
                AccountDto accountDto = tokenHandler.validateToken(token);
                if (accountDto != null) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                            accountDto,
                            accountDto.getPassword(),
                            new ArrayList<>()
                    );
                    accessor.setUser(authentication);
                    System.out.println("WebSocket authenticated user: " + accountDto.getUsername());
                }
            }
        }
        return message;
    }
}
