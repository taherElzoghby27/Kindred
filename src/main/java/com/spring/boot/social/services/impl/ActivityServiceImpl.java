package com.spring.boot.social.services.impl;
import com.spring.boot.social.dto.ActivityDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.mappers.ActivityMapper;
import com.spring.boot.social.models.Activity;
import com.spring.boot.social.repositories.ActivityRepo;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.vm.RequestActivityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {
    private final ActivityRepo activityRepo;

    @Override
    public List<ActivityDto> getAllActivities() {
        List<Activity> activities = activityRepo.findAll();
        if (activities.isEmpty()) {
            throw new BadRequestException("no_activities");
        }
        return activities.stream().map(ActivityMapper.INSTANCE::toActivityDto).toList();
    }

    @Override
    public void logActivity(RequestActivityVm requestActivityVm) {
        if (Objects.isNull(requestActivityVm.getActivityMessage()) || Objects.isNull(requestActivityVm.getActivity())) {
            throw new BadRequestException("activity.not.empty");
        }
        Activity activity = ActivityMapper.INSTANCE.toActivity(requestActivityVm);
        activityRepo.save(activity);
    }
}
