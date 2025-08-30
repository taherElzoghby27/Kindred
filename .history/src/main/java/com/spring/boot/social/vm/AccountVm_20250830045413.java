package com.spring.boot.social.vm;

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
@Schema(description = "Account view model for display purposes")
public class AccountVm {
    @Schema(description = "First name of the user", example = "John")
    private String firstName;
    
    @Schema(description = "Last name of the user", example = "Doe")
    private String lastName;
    
    @Schema(description = "Additional account details")
    private AccountDetailsVm accountDetails;
}
