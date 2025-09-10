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
    @Schema(description = "Unique identifier for the account", example = "1553453")
    private Long id;

    @Schema(description = "Username for the account", example = "john_doe")
    private String username;

    @NotEmpty(message = "empty.email")
    @Email(message = "email.format")
    @Schema(
            description = "Email address for the account",
            example = "john.doe@example.com",
            requiredMode = Schema.RequiredMode.REQUIRED
    )
    private String email;

    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$",
            message = "error.password"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Schema(
            description = "Password must be at least 7 characters long and include: "
                          + "• one uppercase letter, "
                          + "• one lowercase letter, "
                          + "• one digit, "
                          + "• and one special character.",
            example = "MyPass123!",
            requiredMode = Schema.RequiredMode.REQUIRED
    )

    private String password;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private Long enabled;

    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Additional account details")
    private AccountDetailsDto accountDetails;
}
