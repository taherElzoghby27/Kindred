package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.vm.PostsVmResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create-post")
    public SuccessDto<ResponseEntity<PostDto>> createPost(@Valid @RequestBody PostDto postDto) {
        return new SuccessDto<>(
                ResponseEntity.created(URI.create("/create-post")).body(postService.createPost(postDto))
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-my-posts")
    public SuccessDto<ResponseEntity<PostsVmResponse>> getMyPosts(@RequestParam int page, @RequestParam int pageSize) {
        return new SuccessDto<>(
                ResponseEntity.ok(postService.getPostsByAccount(page, pageSize))
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all-posts")
    public SuccessDto<ResponseEntity<PostsVmResponse>> getAllPosts(@RequestParam int page, @RequestParam int pageSize) {
        return new SuccessDto<>(
                ResponseEntity.ok(postService.getPosts(page, pageSize))
        );
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete-post")
    public SuccessDto<ResponseEntity<SuccessDto>> deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        return new SuccessDto<>(
                ResponseEntity.ok(new SuccessDto<>("Successfully Deleted"))
        );
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-post")
    public SuccessDto<ResponseEntity<PostDto>> getPost(@RequestParam Long id) {
        return new SuccessDto<>(
                ResponseEntity.ok(postService.getPost(id))
        );
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-post")
    public SuccessDto<ResponseEntity<PostDto>> updatePost(@RequestBody PostDto postDto) {
        return new SuccessDto<>(
                ResponseEntity.ok(postService.updatePost(postDto))
        );
    }
}
