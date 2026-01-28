package com.spring.boot.social.services;

import com.spring.boot.social.dto.NotificationDto;
import com.spring.boot.social.vm.NotificationRequestVm;

import java.util.List;

public interface NotificationService {
    void saveNotification(NotificationRequestVm notificationRequestVm);

    void markNotificationAsRead(Long notificationId);

    List<NotificationDto> getNotifications();
}
