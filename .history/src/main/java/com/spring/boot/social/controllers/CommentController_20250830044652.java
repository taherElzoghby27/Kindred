package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.CommentService;
import com.spring.boot.social.vm.CommentRequestVm;
import com.spring.boot.social.vm.CommentResponseVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
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
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Comment management APIs")
public class CommentController {

    private final CommentService commentService;


    @Operation(summary = "Create Comment", description = "Create a new comment on a post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Comment created successfully",
                    content = @Content(schema = @Schema(implementation = CommentResponseVm.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create-comment")
    public SuccessDto<ResponseEntity<CommentResponseVm>> createComment(@Valid @RequestBody CommentRequestVm commentRequestVm) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/create-comment")).body(commentService.createComment(commentRequestVm))
        );
    }

    @Operation(summary = "Update Comment", description = "Update an existing comment")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment updated successfully",
                    content = @Content(schema = @Schema(implementation = CommentResponseVm.class))),
            @ApiResponse(responseCode = "400", description = "Invalid input data"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-comment")
    public SuccessDto<ResponseEntity<CommentResponseVm>> updateComment(@Valid @RequestBody CommentRequestVm commentRequestVm) {
        return new SuccessDto<>(
                ResponseEntity.ok(commentService.updateComment(commentRequestVm))
        );
    }

    @Operation(summary = "Delete Comment", description = "Delete a comment by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comment deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid comment ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Comment not found")
    })
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete-comment")
    public SuccessDto<ResponseEntity<String>> deleteComment(@Valid @RequestParam Long commentId) {
        commentService.deleteComment(commentId);
        return new SuccessDto<>(
                ResponseEntity.ok("Successfully Deleted")
        );
    }

    @Operation(summary = "Get Comments by Post", description = "Retrieve all comments for a specific post")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Comments retrieved successfully",
                    content = @Content(schema = @Schema(implementation = CommentResponseVm.class))),
            @ApiResponse(responseCode = "400", description = "Invalid post ID"),
            @ApiResponse(responseCode = "401", description = "Unauthorized"),
            @ApiResponse(responseCode = "404", description = "Post not found")
    })
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-comments")
    public SuccessDto<ResponseEntity<List<CommentResponseVm>>> getComments(@Valid @RequestParam Long postId) {
        return new SuccessDto<>(
                ResponseEntity.ok(commentService.getCommentsByPostId(postId))
        );
    }
}
