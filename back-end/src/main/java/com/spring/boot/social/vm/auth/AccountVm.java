package com.spring.boot.social.vm.auth;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Account view model for display purposes")
public class AccountVm {
    private Long id;
    @Schema(description = "Username for the account", example = "john_doe")
    private String username;
    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "User's full name", example = "John Doe")
    @JsonProperty("full_name")
    private String fullName;

    @Schema(description = "User's profile picture URL", example = "https://example.com/profile.jpg")
    @JsonProperty("profile_picture_url")
    private String profilePictureUrl;
    @Schema(description = "User's email", example = "example@example.com")
    private String email;
}
