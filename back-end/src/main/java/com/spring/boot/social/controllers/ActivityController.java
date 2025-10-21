package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.ActivityDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.vm.CommentResponseVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/activities")
@RestController
@RequiredArgsConstructor
@Tag(name = "Activities", description = "Activities Apis")
public class ActivityController {
    private final ActivityService activityService;

    @Operation(summary = "Get all activities", description = "Retrieve all activities for a specific account")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "activities retrieved successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = CommentResponseVm.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "401",
                            description = "Unauthorized"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "activities not found"
                    )
            }
            )
    @PreAuthorize("isAuthenticated()")
    @GetMapping
    public SuccessDto<ResponseEntity<List<ActivityDto>>> getActivities() {
        return new SuccessDto<>(ResponseEntity.ok(activityService.getAllActivities()));
    }

}
