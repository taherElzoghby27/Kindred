package com.spring.boot.social.vm.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ProfileResponseVm {
    private String username;
    private String email;
    @JsonProperty("first_name")
    private String firstName;
    @JsonProperty("last_name")
    private String lastName;
    private Long friends;
    private Long enabled;
    private Long age;
    @JsonProperty("phone_number")
    private String phoneNumber;
    private String address;
    @JsonProperty("full_name")
    private String fullName;
    private LocalDateTime birthday;
    private String bio;
    @JsonProperty("profile_picture_url")
    private String profilePictureUrl;
}
