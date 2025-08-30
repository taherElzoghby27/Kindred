package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Additional account details for user profile")
public class AccountDetailsDto {
    @NotEmpty(message = "empty.phone")
    @Pattern(regexp = "^[0-9]{10,15}$", message = "error.phone")
    @Schema(description = "Phone number (10-15 digits)", example = "1234567890", required = true)
    private String phone;

    @NotEmpty(message = "empty.address")
    @Schema(description = "User's address", example = "123 Main St, City, Country", required = true)
    private String address;

    @NotEmpty(message = "empty.birthDate")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "error.birthDate")
    @Schema(description = "Date of birth in YYYY-MM-DD format", example = "1990-01-01", required = true)
    private String birthDate;

    @NotEmpty(message = "empty.gender")
    @Schema(description = "User's gender", example = "Male", required = true)
    private String gender;

    @Schema(description = "User's profile picture URL", example = "https://example.com/profile.jpg")
    private String profilePicture;

    @Schema(description = "User's bio or description", example = "Software developer passionate about technology")
    private String bio;

    @Schema(description = "User's website URL", example = "https://johndoe.com")
    private String website;

    @Schema(description = "User's location", example = "New York, USA")
    private String location;
}
