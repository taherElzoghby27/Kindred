package com.spring.boot.social.mappers;

import com.spring.boot.social.entity.chat.Chat;
import com.spring.boot.social.vm.chat.ChatResponseVm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ChatMapper {
    ChatMapper INSTANCE = Mappers.getMapper(ChatMapper.class);

    ChatResponseVm toChatResponseVm(Chat chat);
}
