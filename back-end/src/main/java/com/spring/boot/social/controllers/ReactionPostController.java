package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.post.ReactionPostService;
import com.spring.boot.social.vm.PostReactionAccountVm;
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

@RequestMapping("/reaction-post")
@RestController
@RequiredArgsConstructor
@Tag(name = "Post Reactions", description = "Post reaction management APIs")
public class ReactionPostController {
    private final ReactionPostService reactionPostService;


    @Operation(summary = "Add Reaction", description = "Add a reaction to a post")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Reaction added successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PostMapping("/reaction-request")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessDto<PostReactionAccountVm>> reactionRequest(@Valid @RequestBody ReactionRequestVm reactionRequestVm) {
        return ResponseEntity.ok(new SuccessDto<>(reactionPostService.reactionRequest(reactionRequestVm)));
    }

    @Operation(summary = "Remove Reaction", description = "Remove a reaction from a post")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Reaction removed successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Reaction not found")})
    @DeleteMapping("/delete")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessDto<String>> deleteReact(@Valid @RequestParam("post_id") Long postId) {
        reactionPostService.removeReaction(postId);
        return ResponseEntity.ok(new SuccessDto<>("Success"));
    }
}
