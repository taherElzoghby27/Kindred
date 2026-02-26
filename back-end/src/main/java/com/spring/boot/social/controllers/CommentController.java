package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.comment.CommentService;
import com.spring.boot.social.vm.comment.CommentRequestVm;
import com.spring.boot.social.vm.comment.CommentResponseVm;
import com.spring.boot.social.vm.GeneralResponseVm;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
@Tag(name = "Comments", description = "Comment management APIs")
public class CommentController {

    private final CommentService commentService;


    @Operation(summary = "Create Comment", description = "Create a new comment on a post")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Comment created successfully", content = @Content(schema = @Schema(implementation = CommentResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public ResponseEntity<SuccessDto<CommentResponseVm>> createComment(@Valid @RequestBody CommentRequestVm commentRequestVm) {
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDto<>(commentService.createComment(commentRequestVm)));
    }

    @Operation(summary = "Update Comment", description = "Update an existing comment")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Comment updated successfully", content = @Content(schema = @Schema(implementation = CommentResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Comment not found")})
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    public ResponseEntity<SuccessDto<CommentResponseVm>> updateComment(@Valid @RequestBody CommentRequestVm commentRequestVm) {
        return ResponseEntity.ok(new SuccessDto<>(commentService.updateComment(commentRequestVm)));
    }

    @Operation(summary = "Delete Comment", description = "Delete a comment by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Comment deleted successfully"), @ApiResponse(responseCode = "400", description = "Invalid comment ID"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Comment not found")})
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete")
    public ResponseEntity<SuccessDto<String>> deleteComment(@Valid @RequestParam("comment_id") Long commentId) {
        commentService.deleteCommentBasedOnAccount(commentId);
        return ResponseEntity.ok(new SuccessDto<>("Successfully Deleted"));
    }

    @Operation(summary = "Get Comments by Post", description = "Retrieve all comments for a specific post")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Comments retrieved successfully", content = @Content(schema = @Schema(implementation = CommentResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid post ID"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all-comments")
    public ResponseEntity<SuccessDto<GeneralResponseVm<CommentResponseVm>>> getComments(@Valid @RequestParam("post_id") Long postId, @RequestParam int page, @RequestParam("page_size") int pageSize) {
        return ResponseEntity.ok(new SuccessDto<>(commentService.getCommentsByPostId(postId, page, pageSize)));
    }
}
