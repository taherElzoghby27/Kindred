package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.ActivityDto;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.vm.RequestActivityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    @Override
    public List<ActivityDto> getAllActivities() {
        return List.of();
    }

    @Override
    public void logActivity(RequestActivityVm requestActivityVm) {

    }
}
