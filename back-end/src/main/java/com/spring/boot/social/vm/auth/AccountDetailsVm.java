package com.spring.boot.social.vm.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Account details view model for display purposes")
public class AccountDetailsVm {
    @Schema(description = "Unique identifier for account details", example = "1")
    private Long id;

    @Schema(description = "User's full name", example = "John Doe")
    private String fullName;

    @Schema(description = "User's profile picture URL", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;
}
