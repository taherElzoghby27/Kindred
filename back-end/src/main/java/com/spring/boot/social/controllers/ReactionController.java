package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.ReactionPostService;
import com.spring.boot.social.vm.ReactionRequestVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/reaction")
@RestController
@RequiredArgsConstructor
@Tag(name = "Reactions", description = "Post reaction management APIs")
public class ReactionController {
    private final ReactionPostService reactionPostService;


    @Operation(summary = "Add Reaction", description = "Add a reaction to a post")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Reaction added successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PostMapping("/reaction-request")
    @PreAuthorize("isAuthenticated()")
    public SuccessDto<ResponseEntity<String>> reactionRequest(@Valid @RequestBody ReactionRequestVm reactionRequestVm) {
        reactionPostService.reactionRequest(reactionRequestVm);
        return new SuccessDto<>(ResponseEntity.created(URI.create("/reaction-request")).body("Success"));
    }

    @Operation(summary = "Remove Reaction", description = "Remove a reaction from a post")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Reaction removed successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Reaction not found")})
    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public SuccessDto<ResponseEntity<String>> deleteReact(@Valid @RequestParam("post_id") Long postId) {
        reactionPostService.removeReaction(postId);
        return new SuccessDto<>(ResponseEntity.ok("Success"));
    }
}
