package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.comment.ReactionCommentService;
import com.spring.boot.social.vm.comment.ReactionCommentRequestVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/reaction-comment")
@RestController
@RequiredArgsConstructor
@Tag(name = "Comment Reactions", description = "Comment reaction management APIs")
public class ReactionCommentController {
    private final ReactionCommentService reactionCommentService;

    @Operation(summary = "Add Reaction", description = "Add a reaction to a comment")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Reaction added successfully"), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Comment not found")})
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<SuccessDto<String>> reactionRequest(@Valid @RequestBody ReactionCommentRequestVm reactionRequestVm) {
        reactionCommentService.changeReaction(reactionRequestVm);
        return ResponseEntity.ok(new SuccessDto<>("Success"));
    }
}
