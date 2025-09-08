package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Additional account details for user profile")
public class AccountDetailsDto {
    @Schema(description = "Unique identifier for account details", example = "342341")
    private Long id;
    
    @Min(value = 16, message = "error.age")
    @Schema(description = "User's age (minimum 16)", example = "25")
    private Long age;
    
    @Schema(description = "User's phone number", example = "+1234567890")
    private String phoneNumber;
    
    @Schema(description = "User's address", example = "123 Main St, City, Country")
    private String address;
    
    @Schema(description = "User's full name", example = "John Doe")
    private String fullName;
    
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    @Schema(description = "User's birthday in yyyy-MM-dd HH:mm:ss format", example = "1990-01-01 00:00:00")
    private LocalDateTime birthday;
    
    @Schema(description = "User's bio or description", example = "Software developer passionate about technology")
    private String bio;
    
    @Schema(description = "User's profile picture URL", example = "https://example.com/profile.jpg")
    private String profilePictureUrl;
    
    @JsonProperty(value = "account_id")
    @Schema(description = "Associated account ID", example = "1")
    private Long accountId;
}
