package com.spring.boot.social.repositories;

import com.spring.boot.social.entity.security.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileRepo extends JpaRepository<Account, Long> {
}
