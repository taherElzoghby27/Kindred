package com.spring.boot.social.services;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.vm.PostsVmResponse;

public interface PostService {
    PostDto createPost(PostDto postDto);

    PostsVmResponse getPostsByAccount(int page, int size);

    PostsVmResponse getPosts(int page, int size);

    void deletePost(Long id);

    PostDto getPost(Long id);

    PostDto updatePost(PostDto postDto);
}
