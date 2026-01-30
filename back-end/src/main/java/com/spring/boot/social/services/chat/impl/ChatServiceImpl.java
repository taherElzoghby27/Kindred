package com.spring.boot.social.services.chat.impl;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.chat.Chat;
import com.spring.boot.social.entity.chat.ChatParticipant;
import com.spring.boot.social.entity.chat.Message;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.ChatMapper;
import com.spring.boot.social.repositories.chat.ChatRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.chat.ChatService;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import com.spring.boot.social.vm.chat.MessageRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepo chatRepo;
    private final AccountService accountService;

    @Override
    public ChatResponseVm getChat(Long chatId) {
        if (Objects.isNull(chatId)) {
            throw new NotFoundResourceException("chat_id_must_be_not_null");
        }
        Optional<Chat> result = chatRepo.findById(chatId);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("not_found_chat");
        }
        Account currentAccount = accountService.getCurrentAccount();
        if (Objects.equals(currentAccount.getId(), result.get().getChatParticipants().get(0).getAccount().getId()) ||
                Objects.equals(currentAccount.getId(), result.get().getChatParticipants().get(1).getAccount().getId())) {
            return ChatMapper.INSTANCE.toChatResponseVm(result.get());
        }
        throw new NotFoundResourceException("not_found_chat");
    }

    @Override
    public MessageDto sendMessage(MessageRequestVm messageRequestVm) {
        Account currentAccount = accountService.getCurrentAccount();
        Account receiverAccount = accountService.getAccount(messageRequestVm.getReceiverId());
        if (Objects.isNull(receiverAccount)) {
            throw new NotFoundResourceException("account.not_found");
        }
        Chat chat = null;
        Message message = new Message();
        //create chat and 2 participants if not exist and get chat if exist
        chat = getChat(messageRequestVm.getChatId(), chat, currentAccount, receiverAccount);
        //get messages
        List<Message> messages = chat.getMessages();
        //create message
        createMessage(messageRequestVm.getText(), message, chat, currentAccount);
        messages.add(message);
        chat.setMessages(messages);
        chat.setLastMessageAt(LocalDateTime.now());
        chatRepo.save(chat);
        return new MessageDto();
    }

    private void createMessage(String txt, Message message, Chat chat, Account currentAccount) {
        message.setChat(chat);
        message.setSeen(false);
        message.setAccount(currentAccount);
        message.setText(txt);
    }

    private Chat getChat(Long chatId, Chat chat, Account currentAccount, Account receiverAccount) {
        //if found
        if (Objects.nonNull(chatId)) {
            Optional<Chat> result = chatRepo.findById(chatId);
            if (result.isPresent()) {
                chat = result.get();
                if (chat.getChatParticipants().size() < 2) {
                    throw new NotFoundResourceException("something_wrong");
                }
            }
        }
        //if not found
        if (Objects.isNull(chatId) || Objects.isNull(chat)) {
            chat = createChat();
            //add 2 participants
            List<ChatParticipant> chatParticipants = chat.getChatParticipants();
            if (Objects.equals(currentAccount.getId(), receiverAccount.getId())) {
                throw new NotFoundResourceException("accounts_must_be_different");
            }
            ChatParticipant chatParticipant = new ChatParticipant();
            chatParticipant.setAccount(currentAccount);//  current
            chatParticipant.setChat(chat);
            chatParticipants.add(chatParticipant);
            chatParticipant = new ChatParticipant();

            chatParticipant.setAccount(receiverAccount);//receiver
            chatParticipant.setChat(chat);
            chatParticipants.add(chatParticipant);
            chat.setChatParticipants(chatParticipants);
        }
        return chat;
    }

    private Chat createChat() {
        Chat chat = new Chat();
        chat.setLastMessageAt(LocalDateTime.now());
        chatRepo.save(chat);
        return chat;
    }
}
