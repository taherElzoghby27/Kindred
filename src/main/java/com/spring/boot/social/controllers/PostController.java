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
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.created(URI.create("/create-post")).body(postService.createPost(postDto));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-my-posts")
    public ResponseEntity<PostsVmResponse> getMyPosts(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(postService.getPostsByAccount(page, pageSize));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-all-posts")
    public ResponseEntity<PostsVmResponse> getAllPosts(@RequestParam int page, @RequestParam int pageSize) {
        return ResponseEntity.ok(postService.getPosts(page, pageSize));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete-post")
    public ResponseEntity<SuccessDto> deletePost(@RequestParam Long id) {
        postService.deletePost(id);
        return ResponseEntity.ok(new SuccessDto<>("Successfully Deleted"));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-post")
    public ResponseEntity<PostDto> getPost(@RequestParam Long id) {
        return ResponseEntity.ok(postService.getPost(id));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-post")
    public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto) {
        return ResponseEntity.ok(postService.updatePost(postDto));
    }
}
