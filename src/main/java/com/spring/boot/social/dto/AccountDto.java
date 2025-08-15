package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
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
    private String email;
    @NotEmpty(message = "empty.password")
    private String password;
    private Long enabled;
    private String firstName;
    private String lastName;
}
