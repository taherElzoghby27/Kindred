package com.spring.boot.social.vm.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Schema(description = "Account view model for display purposes")
public class AccountVm {
    private Long id;
    @Schema(description = "First name of the user", example = "John")
    private String firstName;

    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;

    @Schema(description = "Additional account details")
    private AccountDetailsVm accountDetails;
}
