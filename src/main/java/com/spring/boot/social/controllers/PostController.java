package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.services.PostService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
