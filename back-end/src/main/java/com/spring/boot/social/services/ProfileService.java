package com.spring.boot.social.services;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.vm.auth.ProfileResponseVm;

public interface ProfileService {
    ProfileResponseVm getProfileResponseVm();

    AccountDto updateProfile(AccountDto accountDto);
}
