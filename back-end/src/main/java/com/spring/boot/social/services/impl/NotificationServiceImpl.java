package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.NotificationDto;
import com.spring.boot.social.repositories.NotificationRepo;
import com.spring.boot.social.services.NotificationService;
import com.spring.boot.social.vm.NotificationRequestVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepo notificationRepo;

    @Override
    public void saveNotification(NotificationRequestVm notificationRequestVm) {

    }

    @Override
    public void markNotificationAsRead(Long notificationId) {

    }

    @Override
    public List<NotificationDto> getNotifications() {
        return List.of();
    }
}
