package com.spring.boot.social.mappers;

import com.spring.boot.social.entity.Notification;
import com.spring.boot.social.vm.NotificationRequestVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface NotificationMapper {
    NotificationMapper INSTANCE = Mappers.getMapper(NotificationMapper.class);

    @Mapping(target = "recipient", ignore = true)
    Notification toNotification(NotificationRequestVm notificationRequestVm);
}
