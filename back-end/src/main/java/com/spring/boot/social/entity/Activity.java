package com.spring.boot.social.entity;

import com.spring.boot.social.utils.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Activity extends BaseEntity<String> {
    @Column(nullable = false)
    private String activityMessage;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ActivityType activity;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Account account;
}
