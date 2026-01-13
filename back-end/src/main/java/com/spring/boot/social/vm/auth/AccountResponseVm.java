package com.spring.boot.social.vm.auth;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@Schema(description = "Account response information after authentication")
public class AccountResponseVm {
    @Schema(description = "Unique identifier for the account", example = "1")
    private Long id;
    
    @Schema(description = "Username for the account", example = "john_doe")
    private String username;
    
    @Schema(description = "Email address for the account", example = "john.doe@example.com")
    private String email;
    
    @Schema(description = "JWT authentication token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}
