package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.post.PostService;
import com.spring.boot.social.vm.PostRequestVm;
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
@RequestMapping("/posts")
@RequiredArgsConstructor
@Tag(name = "Posts", description = "Post management APIs")
public class PostController {
    private final PostService postService;


    @Operation(summary = "Create Post", description = "Create a new post")
    @ApiResponses(value = {@ApiResponse(responseCode = "201", description = "Post created successfully", content = @Content(schema = @Schema(implementation = PostDto.class))), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public ResponseEntity<SuccessDto<String>> createPost(@Valid @RequestBody PostRequestVm postRequestVm) {
        postService.createPost(postRequestVm);
        return ResponseEntity.status(HttpStatus.CREATED).body(new SuccessDto<>("Created post successfully"));
    }

    @Operation(summary = "Get My Posts", description = "Retrieve posts created by the current user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = GeneralResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"), @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-posts")
    public ResponseEntity<SuccessDto<GeneralResponseVm<PostDto>>> getMyPosts(@RequestParam int page, @RequestParam("page_size") int pageSize) {
        return ResponseEntity.ok(new SuccessDto<>(postService.getPostsByAccount(page, pageSize)));
    }

    @Operation(summary = "Get All Posts", description = "Retrieve all posts with pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = GeneralResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"), @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all-posts")
    public ResponseEntity<SuccessDto<GeneralResponseVm<PostDto>>> getAllPosts(@RequestParam int page, @RequestParam("page_size") int pageSize) {
        return ResponseEntity.ok(new SuccessDto<>(postService.getPosts(page, pageSize)));
    }

    @Operation(summary = "search by content on Posts", description = "Retrieve all posts with pagination based on content")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = GeneralResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"), @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts-with-content")
    public ResponseEntity<SuccessDto<GeneralResponseVm<PostDto>>> getPostsByContent(@RequestParam int page, @RequestParam("page_size") int pageSize, @RequestParam String content) {
        return ResponseEntity.ok(new SuccessDto<>(postService.searchByContent(page, pageSize, content)));
    }

    @Operation(summary = "Delete Post", description = "Delete a post by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post deleted successfully"), @ApiResponse(responseCode = "400", description = "Invalid post ID"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public ResponseEntity<SuccessDto<String>> deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new SuccessDto<>("Successfully Deleted"));
    }

    @Operation(summary = "Get Post", description = "Retrieve a specific post by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post retrieved successfully", content = @Content(schema = @Schema(implementation = PostDto.class))), @ApiResponse(responseCode = "400", description = "Invalid post ID"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/{id}")
    public ResponseEntity<SuccessDto<PostDto>> getPost(@PathVariable Long id) {
        return ResponseEntity.ok(new SuccessDto<>(postService.getPostDto(id)));
    }

    @Operation(summary = "Update Post", description = "Update an existing post")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post updated successfully", content = @Content(schema = @Schema(implementation = PostDto.class))), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    public ResponseEntity<SuccessDto<PostDto>> updatePost(@Valid @RequestBody PostRequestVm postRequestVm) {
        return ResponseEntity.ok(new SuccessDto<>(postService.updatePost(postRequestVm)));
    }
}
