package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.NotificationDto;
import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.Notification;
import com.spring.boot.social.mappers.NotificationMapper;
import com.spring.boot.social.repositories.NotificationRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.NotificationService;
import com.spring.boot.social.vm.NotificationRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepo notificationRepo;
    private final AccountService accountService;

    @Override
    public void saveNotification(NotificationRequestVm notificationRequestVm) {
        Notification notification = NotificationMapper.INSTANCE.toNotification(notificationRequestVm);
        notification.setRead(false);
        //get recipient by id
        Account recipient = accountService.getAccount(notificationRequestVm.getRecipientId());
        notification.setRecipient(recipient);
        //save notification in db
        notificationRepo.save(notification);
    }

    @Override
    public void markNotificationAsRead(Long notificationId) {

    }

    @Override
    public List<NotificationDto> getNotifications() {
        return List.of();
    }
}
