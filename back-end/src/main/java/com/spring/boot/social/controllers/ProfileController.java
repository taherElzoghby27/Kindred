package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.AuthService;
import com.spring.boot.social.services.ProfileService;
import com.spring.boot.social.vm.GeneralResponseVm;
import com.spring.boot.social.vm.auth.AccountResponseVm;
import com.spring.boot.social.vm.auth.ProfileResponseVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/profile")
@RestController
@RequiredArgsConstructor
@Tag(name = "Profile", description = "Profile management APIs")
public class ProfileController {
    private final ProfileService profileService;

    @Operation(summary = "profile", description = "Retrieve profile details")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "profile retrieved successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = GeneralResponseVm.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    )
            }
    )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public SuccessDto<ResponseEntity<ProfileResponseVm>> getProfile() {
        return new SuccessDto<>(
                ResponseEntity.ok(profileService.getProfile())
        );
    }

    @Operation(summary = "Update Profile", description = "Update existing user account information")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Account updated successfully",
                    content = @Content(schema = @Schema(implementation = AccountDto.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "404", description = "Account not found")
    })
    @PutMapping
    public SuccessDto<ResponseEntity<AccountDto>> updateAccount(@Valid @RequestBody AccountDto accountDto) {
        return new SuccessDto<>(ResponseEntity.ok(profileService.updateProfile(accountDto)));
    }
}
