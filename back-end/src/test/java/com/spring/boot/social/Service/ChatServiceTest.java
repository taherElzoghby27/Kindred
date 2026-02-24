package com.spring.boot.social.Service;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.chat.Chat;
import com.spring.boot.social.entity.chat.ChatParticipant;
import com.spring.boot.social.entity.chat.Message;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.repositories.chat.ChatRepo;
import com.spring.boot.social.repositories.chat.MessageRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.chat.impl.ChatServiceImpl;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import com.spring.boot.social.vm.chat.MessageRequestVm;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class ChatServiceTest {
    @Mock
    private ChatRepo chatRepo;
    @Mock
    private MessageRepo messageRepo;
    @Mock
    private AccountService accountService;
    @InjectMocks
    private ChatServiceImpl chatService;
    private Chat chat;
    private Account sender;
    private Account receiver;
    private Message message;


    @BeforeEach
    public void setUp() {
        sender = Account.builder().username("taher").build();
        sender.setId(1L);
        receiver = Account.builder().username("ahmed").build();
        receiver.setId(2L);
        ChatParticipant chatParticipant1 = ChatParticipant.builder().account(sender).build();
        ChatParticipant chatParticipant2 = ChatParticipant.builder().account(receiver).build();
        chatParticipant1.setId(1L);
        chatParticipant2.setId(2L);
        message = Message.builder().text("hello").sender(sender).seen(false).build();
        message.setId(1L);
        chat = Chat.builder().messages(List.of(message)).chatParticipants(List.of(chatParticipant1, chatParticipant2)).build();
        chat.setId(1L);
        message.setChat(chat);
    }

    @Test
    public void givenChatId_whenGetChat_thenGetChat() {
        ChatResponseVm chatResponse = ChatResponseVm.builder().id(1L).build();
        when(chatRepo.findById(1L)).thenReturn(Optional.of(chat));
        when(accountService.getCurrentAccount()).thenReturn(sender);
        Assertions.assertEquals(chatResponse.getId(), chatService.getChatResponseVm(1L).getId());
    }

    @Test
    public void givenChatId_whenGetChat_thenGetNull() {
        when(chatRepo.findById(1L)).thenReturn(Optional.empty());
        Assertions.assertThrows(NotFoundResourceException.class, () -> chatService.getChatResponseVm(1L), "not found chat");
    }

    @Test
    public void givenFirstMessage_whenSendIt_thenSuccess() {
        MessageRequestVm messageRequestVm = MessageRequestVm.builder().text("hello").receiverId(2L).build();
        MessageDto responseMessageDto = MessageDto.builder().id(1L).text("hello").build();
        when(accountService.getCurrentAccount()).thenReturn(sender);
        when(accountService.getAccountById(2L)).thenReturn(receiver);
        when(messageRepo.save(any(Message.class))).thenReturn(message);
        when(chatRepo.save(any(Chat.class))).thenReturn(chat);
        MessageDto messageSavedResponse = chatService.sendMessage(messageRequestVm);
        Assertions.assertEquals(responseMessageDto.getText(), messageSavedResponse.getText());
    }

    @Test
    public void givenMessageWithSenderSameReceiver_whenSendIt_thenThrowException() {
        MessageRequestVm messageRequestVm = MessageRequestVm.builder().text("hello").receiverId(1L).build();
        ChatParticipant chatParticipant1 = ChatParticipant.builder().account(sender).build();
        chat.setChatParticipants(List.of(chatParticipant1, chatParticipant1));
        when(accountService.getCurrentAccount()).thenReturn(sender);
        when(accountService.getAccountById(1L)).thenReturn(sender);
        Assertions.assertThrows(NotFoundResourceException.class, () -> chatService.sendMessage(messageRequestVm));
    }

    @Test
    public void giveMessage_whenSendIt_thenSuccess() {
        Message message1 = Message.builder().text("hello1").sender(sender).build();
        chat.setMessages(List.of(message1, message));
        MessageRequestVm messageRequestVm = MessageRequestVm.builder().text("hello").receiverId(2L).build();
        when(accountService.getCurrentAccount()).thenReturn(sender);
        when(accountService.getAccountById(2L)).thenReturn(receiver);
        when(messageRepo.save(any(Message.class))).thenReturn(message);
        when(chatRepo.save(any(Chat.class))).thenReturn(chat);
        when(chatRepo.findById(1L)).thenReturn(Optional.of(chat));
        chatService.sendMessage(messageRequestVm);
        ChatResponseVm chatResult = chatService.getChatResponseVm(1L);
        Assertions.assertEquals(2, chatResult.getMessages().size());
    }
}
