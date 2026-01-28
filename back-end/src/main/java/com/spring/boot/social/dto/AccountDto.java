package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.security.Principal;
import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Account information for user registration and updates")
public class AccountDto implements Principal {
    @Schema(description = "Unique identifier for the account", example = "1553453")
    private Long id;
    @Schema(description = "Username for the account", example = "john_doe")
    private String username;

    @NotEmpty(message = "empty.email")
    @Email(message = "email.format")
    @Schema(description = "Email address for the account", example = "john.doe@example.com", requiredMode = Schema.RequiredMode.REQUIRED)
    private String email;

    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$", message = "error.password")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Password must be at least 7 characters long and include: " + "• one uppercase letter, " + "• one lowercase letter, " + "• one digit, " + "• and one special character.", example = "MyPass123!", requiredMode = Schema.RequiredMode.REQUIRED)

    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long enabled;
    @Schema(description = "First name of the user", example = "John")
    @NotEmpty(message = "empty.first_name")
    @JsonProperty("first_name")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    @NotEmpty(message = "empty.last_name")
    @JsonProperty("last_name")
    private String lastName;

    @Min(value = 16, message = "error.age")
    @Schema(description = "User's age (minimum 16)", example = "25")
    private Long age;

    @Schema(description = "User's phone number", example = "+1234567890")
    @JsonProperty("phone_number")
    private String phoneNumber;

    @Schema(description = "User's address", example = "123 Main St, City, Country")
    private String address;

    @Schema(description = "User's full name", example = "John Doe")
    @JsonProperty("full_name")
    private String fullName;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "User's birthday in yyyy-MM-dd HH:mm:ss format", example = "1990-01-01 00:00:00")
    private LocalDateTime birthday;

    @Schema(description = "User's bio or description", example = "Software developer passionate about technology")
    private String bio;

    @Schema(description = "User's profile picture URL", example = "https://example.com/profile.jpg")
    @JsonProperty("profile_picture_url")
    private String profilePictureUrl;

    @Override
    public String getName() {
        return this.username;
    }
}
