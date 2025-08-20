package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.vm.PostsVmResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/posts")
@RequiredArgsConstructor
public class PostController {
    private final PostService postService;


    @PostMapping("/create-post")
    public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto) {
        return ResponseEntity.created(URI.create("/create-post")).body(postService.createPost(postDto));
    }

    @GetMapping("/get-posts")
    public ResponseEntity<PostsVmResponse> getPosts(int page, int pageSize) {
        return ResponseEntity.ok(postService.getPosts(page, pageSize));
    }
}
