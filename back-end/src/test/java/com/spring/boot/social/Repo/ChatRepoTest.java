package com.spring.boot.social.Repo;

import com.spring.boot.social.Config.TestConfig;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.chat.Chat;
import com.spring.boot.social.entity.chat.ChatParticipant;
import com.spring.boot.social.entity.chat.Message;
import com.spring.boot.social.repositories.chat.ChatParticipantRepo;
import com.spring.boot.social.repositories.chat.ChatRepo;
import com.spring.boot.social.repositories.chat.MessageRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@DataJpaTest
@Import(TestConfig.class)
public class ChatRepoTest {
    @Autowired
    private ChatRepo chatRepo;
    @Autowired
    private MessageRepo messageRepo;
    @Autowired
    private ChatParticipantRepo chatParticipantRepo;
    private Chat chat;

    @BeforeEach
    public void setUp() {
        chat = Chat.builder().lastMessageAt(LocalDateTime.parse("2007-12-03T10:15:30")).build();
    }

    @Test
    public void givenChat_whenSaveIt_thenReturnChat() {
        //Arrange
        //Act
        Chat foundChat = chatRepo.save(chat);
        //Assert
        Assertions.assertNotNull(foundChat.getId());
    }

    @Test
    public void givenChat_whenSaveIt_thenLastMessageAtEquals() {
        //Arrange
        //Act
        Chat foundChat = chatRepo.save(chat);
        //Assert
        Assertions.assertEquals(foundChat.getLastMessageAt(), chat.getLastMessageAt());
    }

    @Test
    public void givenParticipantsToChat_whenSaveIt_thenReflectInDb() {
        //Arrange
        Account account = Account.builder()
                .username("taher")
                .email("taher@gmail.com")
                .firstName("Taher")
                .age(23L)
                .bio("test")
                .password("test12-pass")
                .build();
        ChatParticipant participant1 = ChatParticipant.builder().chat(chat).account(account).build();
        chat.setChatParticipants(List.of(participant1));
        //Act
        Chat foundChat = chatRepo.save(chat);
        //Assert
        Assertions.assertAll(
                "equals",
                () -> Assertions.assertEquals(1, foundChat.getChatParticipants().size()),
                () -> Assertions.assertEquals(account.getEmail(), foundChat.getChatParticipants().get(0).getAccount().getEmail())
        );
    }

    @Test
    public void givenParticipantsToChat_whenSaveIt_thenReflectInDbChatParticipantSide() {
        //Arrange
        Account account = Account.builder()
                .username("taher")
                .email("taher@gmail.com")
                .firstName("Taher")
                .age(23L)
                .bio("test")
                .password("test12-pass")
                .build();
        ChatParticipant participant1 = ChatParticipant.builder().chat(chat).account(account).build();
        chat.setChatParticipants(List.of(participant1));
        //Act
        Chat foundChat = chatRepo.save(chat);
        ChatParticipant foundChatParticipant = chatParticipantRepo.findById(foundChat.getChatParticipants().get(0).getId()).get();
        //Assert
        Assertions.assertNotNull(foundChatParticipant.getId());
    }

    @Test
    public void givenMessageToChat_whenSaveIt_thenReflectInDb() {
        //Arrange
        Account account = Account.builder()
                .username("taher")
                .email("taher@gmail.com")
                .firstName("Taher")
                .age(23L)
                .bio("test")
                .password("test12-pass")
                .build();
        Message message = Message.builder()
                .text("welcome guys")
                .account(account)
                .account(account)
                .build();
        chat.setMessages(List.of(message));
        //Act
        Chat foundChat = chatRepo.save(chat);
        //Assert
        Assertions.assertAll(
                "equals",
                () -> Assertions.assertEquals(1, foundChat.getMessages().size()),
                () -> Assertions.assertEquals(message.getText(), foundChat.getMessages().get(0).getText())
        );
    }

    @Test
    public void givenMessageToChat_whenSaveIt_thenReflectInDbMessageSide() {
        //Arrange
        Account account = Account.builder()
                .username("taher")
                .email("taher@gmail.com")
                .firstName("Taher")
                .age(23L)
                .bio("test")
                .password("test12-pass")
                .build();
        Message message = Message.builder()
                .text("welcome guys")
                .account(account)
                .account(account)
                .build();
        chat.setMessages(List.of(message));
        //Act
        Chat foundChat = chatRepo.save(chat);
        Message foundMessage = messageRepo.findById(foundChat.getMessages().get(0).getId()).get();
        //Assert
        Assertions.assertNotNull(foundMessage.getId());
    }

    @Test
    public void givenChat_whenDeleteIt_thenMessagesAndParticipantsDeleted() {
        //Arrange
        //Arrange
        Account account = Account.builder()
                .username("taher")
                .email("taher@gmail.com")
                .firstName("Taher")
                .age(23L)
                .bio("test")
                .password("test12-pass")
                .build();
        Message message = Message.builder()
                .text("welcome guys")
                .account(account)
                .account(account)
                .build();
        chat.setMessages(List.of(message));
        ChatParticipant participant1 = ChatParticipant.builder().chat(chat).account(account).build();
        chat.setChatParticipants(List.of(participant1));
        Chat foundChat = chatRepo.save(chat);
        //Act
        chatRepo.deleteById(foundChat.getId());
        //Assert
        Assertions.assertAll("deleted",
                () -> Assertions.assertEquals(Optional.empty(), messageRepo.findById(foundChat.getMessages().get(0).getId())),
                () -> Assertions.assertEquals(Optional.empty(), chatParticipantRepo.findById(foundChat.getChatParticipants().get(0).getId()))
        );
    }
}
