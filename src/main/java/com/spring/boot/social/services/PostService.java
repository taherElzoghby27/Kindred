package com.spring.boot.social.services;

import com.spring.boot.social.dto.PostDto;

import java.util.List;

public interface PostService {
    PostDto createPost(PostDto postDto);

    List<PostDto> getPosts();

    void deletePost(Long id);

    PostDto getPost(Long id);

    PostDto updatePost(PostDto postDto);
}
