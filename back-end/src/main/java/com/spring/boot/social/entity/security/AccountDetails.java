package com.spring.boot.social.entity.security;

import com.spring.boot.social.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountDetails extends BaseEntity<String> {
    @Min(value = 16, message = "error.age")
    private Long age;
    private String phoneNumber;
    private String address;
    private String fullName;
    private LocalDateTime birthday;
    private String bio;
    private String profilePictureUrl;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Account account;
}
