package com.spring.boot.social.dto;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
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
public class AccountDto {
    private Long id;
    private String username;
    @NotEmpty(message = "empty.email")
    @Email(message = "email.format")
    private String email;
    @Pattern(
            regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$",
            message = "error.password"
    )
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    private Long enabled;
    private String firstName;
    private String lastName;
    private AccountDetailsDto accountDetails;
}
