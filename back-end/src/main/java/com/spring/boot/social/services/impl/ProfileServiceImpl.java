package com.spring.boot.social.services.impl;

import com.spring.boot.social.repositories.ProfileRepo;
import com.spring.boot.social.services.ProfileService;
import com.spring.boot.social.vm.auth.ProfileResponseVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepo profileRepo;
    @Override
    public ProfileResponseVm getProfile() {
        return null;
    }
}
