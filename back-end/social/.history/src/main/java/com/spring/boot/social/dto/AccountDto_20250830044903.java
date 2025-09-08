package com.spring.boot.social.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
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
@Schema(description = "Account information for user registration and updates")
public class AccountDto {
    @Schema(description = "Unique identifier for the account", example = "1")
    private Long id;
    
    @Schema(description = "Username for the account", example = "john_doe")
    private String username;
    
    @NotEmpty(message = "empty.email")
    @Email(message = "email.format")
    @Schema(description = "Email address for the account", example = "john.doe@example.com", required = true)
    private String email;
    
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$",
            message = "error.password"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(description = "Password for the account (min 7 chars, must contain uppercase, lowercase, digit, and special char)", example = "MyPass123!", required = true)
    private String password;
    
    @Schema(description = "Account status (1 for enabled, 0 for disabled)", example = "1")
    private Long enabled;
    
    @Schema(description = "First name of the user", example = "John")
    private String firstName;
    
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;
    
    @Schema(description = "Additional account details")
    private AccountDetailsDto accountDetails;
}
