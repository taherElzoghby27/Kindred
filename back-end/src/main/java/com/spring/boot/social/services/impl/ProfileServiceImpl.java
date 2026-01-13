package com.spring.boot.social.services.impl;

import com.spring.boot.social.entity.security.Account;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.ProfileMapper;
import com.spring.boot.social.repositories.ProfileRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.ProfileService;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import com.spring.boot.social.vm.auth.ProfileResponseVm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProfileServiceImpl implements ProfileService {
    private final ProfileRepo profileRepo;
    private final AccountService accountService;

    @Override
    public ProfileResponseVm getProfile() {
        Account account = accountService.getCurrentAccount();
        Optional<Account> acc = profileRepo.findById(account.getId());
        if (acc.isEmpty()) {
            throw new NotFoundResourceException("account.not_found");
        }
        Long friends = profileRepo.nOfFriends(account.getId(), FriendStatusEnum.ACCEPTED);
        ProfileResponseVm profileResponseVm = ProfileMapper.PROFILE_MAPPER.toProfileResponseVm(acc.get());
        profileResponseVm.setFriends(friends);
        return profileResponseVm;
    }
}
