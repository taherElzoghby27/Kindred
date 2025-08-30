package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class AccountDetailsDto {
    private Long id;
    @Min(value = 16, message = "error.age")
    private Long age;
    private String phoneNumber;
    private String address;
    private String fullName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime birthday;
    private String bio;
    private String profilePictureUrl;
    @JsonProperty(value = "account_id")
    private Long accountId;
}
