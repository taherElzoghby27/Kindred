package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.PostService;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

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
    public SuccessDto<ResponseEntity<String>> createPost(@Valid @RequestBody PostRequestVm postRequestVm) {
        postService.createPost(postRequestVm);
        return new SuccessDto<>(ResponseEntity.created(URI.create("/create-post")).body("Created post successfully"));
    }

    @Operation(summary = "Get My Posts", description = "Retrieve posts created by the current user")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = GeneralResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"), @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/my-posts")
    public SuccessDto<ResponseEntity<GeneralResponseVm<PostDto>>> getMyPosts(@RequestParam int page, @RequestParam("page_size") int pageSize) {
        return new SuccessDto<>(ResponseEntity.ok(postService.getPostsByAccount(page, pageSize)));
    }

    @Operation(summary = "Get All Posts", description = "Retrieve all posts with pagination")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Posts retrieved successfully", content = @Content(schema = @Schema(implementation = GeneralResponseVm.class))), @ApiResponse(responseCode = "400", description = "Invalid pagination parameters"), @ApiResponse(responseCode = "401", description = "Unauthorized")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/all-posts")
    public SuccessDto<ResponseEntity<GeneralResponseVm<PostDto>>> getAllPosts(@RequestParam int page, @RequestParam("page_size") int pageSize) {
        return new SuccessDto<>(ResponseEntity.ok(postService.getPosts(page, pageSize)));
    }

    @Operation(summary = "search by content on Posts", description = "Retrieve all posts with pagination based on content")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Posts retrieved successfully",
                            content = @Content(
                                    schema = @Schema(
                                            implementation = GeneralResponseVm.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Invalid pagination parameters"
                    ),
                    @ApiResponse(
                            responseCode = "401", description = "Unauthorized"
                    )
            }
            )
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/posts-with-content")
    public SuccessDto<ResponseEntity<GeneralResponseVm<PostDto>>> getPostsByContent(@RequestParam int page,
                                                                                    @RequestParam("page_size") int pageSize,
                                                                                    @RequestParam String content) {
        return new SuccessDto<>(ResponseEntity.ok(postService.searchByContent(page, pageSize, content)));
    }

    @Operation(summary = "Delete Post", description = "Delete a post by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post deleted successfully"), @ApiResponse(responseCode = "400", description = "Invalid post ID"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @DeleteMapping
    public SuccessDto<ResponseEntity<String>> deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        return new SuccessDto<>(ResponseEntity.ok("Successfully Deleted"));
    }

    @Operation(summary = "Get Post", description = "Retrieve a specific post by ID")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post retrieved successfully", content = @Content(schema = @Schema(implementation = PostDto.class))), @ApiResponse(responseCode = "400", description = "Invalid post ID"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/post/{id}")
    public SuccessDto<ResponseEntity<PostDto>> getPost(@PathVariable Long id) {
        return new SuccessDto<>(ResponseEntity.ok(postService.getPost(id)));
    }

    @Operation(summary = "Update Post", description = "Update an existing post")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Post updated successfully", content = @Content(schema = @Schema(implementation = PostDto.class))), @ApiResponse(responseCode = "400", description = "Invalid input data"), @ApiResponse(responseCode = "401", description = "Unauthorized"), @ApiResponse(responseCode = "404", description = "Post not found")})
    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update")
    public SuccessDto<ResponseEntity<PostDto>> updatePost(@Valid @RequestBody PostRequestVm postRequestVm) {
        return new SuccessDto<>(ResponseEntity.ok(postService.updatePost(postRequestVm)));
    }
}
