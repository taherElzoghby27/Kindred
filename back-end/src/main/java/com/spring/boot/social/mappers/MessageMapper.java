package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.chat.MessageDto;
import com.spring.boot.social.entity.chat.Message;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface MessageMapper {
    MessageMapper INSTANCE = Mappers.getMapper(MessageMapper.class);

    MessageDto toMessageDto(Message message);
}
