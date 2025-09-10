package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.ActivityDto;
import com.spring.boot.social.models.Activity;
import com.spring.boot.social.vm.RequestActivityVm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ActivityMapper {
    ActivityMapper INSTANCE = Mappers.getMapper(ActivityMapper.class);

    ActivityDto toActivityDto(Activity activity);

    Activity toActivity(RequestActivityVm requestActivityVm);
}
