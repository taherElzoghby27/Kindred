package com.spring.boot.social.utils;

import com.spring.boot.social.dto.AccountDto;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {
    private SecurityUtils() {
    }

    public static AccountDto getCurrentAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new AuthenticationCredentialsNotFoundException("no.authenticated.user");
        }

        Object principal = authentication.getPrincipal();
        if (!(principal instanceof AccountDto)) {
            throw new BadCredentialsException("Unexpected principal type: " + principal.getClass().getName());
        }

        return (AccountDto) principal;
    }

//    public static List<RoleEnum> getCurrentRole() {
//        AccountDto account = getCurrentAccount();
//        return account.getRoles().stream().map(role -> RoleEnum.valueOf(role.getRole())).toList();
//    }

//    public static boolean hasRole(RoleEnum roleEnum) {
//        AccountDto account = getCurrentAccount();
//        return account.getRoles().stream().anyMatch(role -> role.getRole().equals(roleEnum.toString()));
//    }
}
