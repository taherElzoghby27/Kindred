package com.spring.boot.social.services;

import com.spring.boot.social.dto.ActivityDto;
import com.spring.boot.social.vm.RequestActivityVm;

import java.util.List;

public interface ActivityService {
    List<ActivityDto> getAllActivities();

    void logActivity(RequestActivityVm requestActivityVm);
}
