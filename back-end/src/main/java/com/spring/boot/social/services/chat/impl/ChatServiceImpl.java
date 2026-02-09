package com.spring.boot.social.services.chat.impl;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.chat.Chat;
import com.spring.boot.social.entity.chat.ChatParticipant;
import com.spring.boot.social.entity.chat.Message;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.mappers.ChatMapper;
import com.spring.boot.social.mappers.MessageMapper;
import com.spring.boot.social.repositories.chat.ChatParticipantRepo;
import com.spring.boot.social.repositories.chat.ChatRepo;
import com.spring.boot.social.repositories.chat.MessageRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.chat.ChatService;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import com.spring.boot.social.vm.chat.MessageRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {
    private final ChatRepo chatRepo;
    private final ChatParticipantRepo chatParticipantRepo;
    private final AccountService accountService;
    private final MessageRepo messageRepo;

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
        if (Objects.equals(currentAccount.getId(), result.get().getChatParticipants().get(0).getAccount().getId())
                || Objects.equals(currentAccount.getId(), result.get().getChatParticipants().get(1).getAccount().getId())) {
            return ChatMapper.INSTANCE.toChatResponseVm(result.get());
        }
        throw new NotFoundResourceException("not_found_chat");
    }

    @Override
    @Transactional
    public MessageDto sendMessage(MessageRequestVm messageRequestVm) {
        //sender
        Account senderAccount = accountService.getCurrentAccount();
        //receiver
        Account receiverAccount = accountService.getAccount(messageRequestVm.getReceiverId());
        validate(receiverAccount);
        //create chat and 2 participants if not exist and get chat if exist
        Chat chat = getChat(messageRequestVm.getChatId(), senderAccount, receiverAccount);
        //create message
        Message message = createMessage(messageRequestVm.getText(), chat, senderAccount);
        messageRepo.save(message);
        chatRepo.save(chat);
        MessageDto messageDto = MessageMapper.INSTANCE.toMessageDto(message);
        messageDto.setReceiver(AccountMapper.ACCOUNT_MAPPER.toAccountVm(receiverAccount));
        return messageDto;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private static void validate(Account receiverAccount) {
        if (Objects.isNull(receiverAccount)) {
            throw new NotFoundResourceException("account.not_found");
        }
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Message createMessage(String txt, Chat chat, Account senderAccount) {
        Message message = new Message();
        message.setChat(chat);
        message.setSeen(false);
        message.setSender(senderAccount);
        message.setText(txt);
        chat.getMessages().add(message);
        return message;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Chat getChat(Long chatId, Account senderAccount, Account receiverAccount) {
        Chat chat = null;
        //if found
        if (Objects.nonNull(chatId)) {
            Optional<Chat> result = chatRepo.findById(chatId);
            if (result.isPresent()) {
                chat = result.get();
                if (chatParticipantRepo.getParticipantsCount(chatId) < 2) {
                    throw new NotFoundResourceException("something_wrong");
                }
            }
        }
        //if not found
        if (Objects.isNull(chatId) || Objects.isNull(chat)) {
            chat = createChat();
            //add 2 participants
            addChatParticipants(senderAccount, receiverAccount, chat);
        }
        return chat;
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private static void addChatParticipants(Account senderAccount, Account receiverAccount, Chat chat) {
        List<ChatParticipant> chatParticipants = chat.getChatParticipants();
        if (Objects.equals(senderAccount.getId(), receiverAccount.getId())) {
            throw new NotFoundResourceException("accounts_must_be_different");
        }
        ChatParticipant chatParticipant = new ChatParticipant();
        chatParticipant.setAccount(senderAccount);//  current
        chatParticipant.setChat(chat);
        chatParticipants.add(chatParticipant);
        chatParticipant = new ChatParticipant();
        chatParticipant.setAccount(receiverAccount);//receiver
        chatParticipant.setChat(chat);
        chatParticipants.add(chatParticipant);
        chat.setChatParticipants(chatParticipants);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    private Chat createChat() {
        Chat chat = new Chat();
        chatRepo.save(chat);
        return chat;
    }
}
