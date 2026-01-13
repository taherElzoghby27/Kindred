package com.spring.boot.social.mappers;

import com.spring.boot.social.entity.Account;
import com.spring.boot.social.vm.auth.ProfileResponseVm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProfileMapper {
    ProfileMapper PROFILE_MAPPER = Mappers.getMapper(ProfileMapper.class);

    ProfileResponseVm toProfileResponseVm(Account account);
}
