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
}
