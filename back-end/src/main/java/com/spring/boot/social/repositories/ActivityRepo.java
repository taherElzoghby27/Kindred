package com.spring.boot.social.repositories;

import com.spring.boot.social.entity.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActivityRepo extends JpaRepository<Activity, Long> {
    List<Activity> findActivitiesByAccountId(Long accountId);
}
