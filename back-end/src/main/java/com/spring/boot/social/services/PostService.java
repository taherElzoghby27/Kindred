package com.spring.boot.social.services;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.vm.PostRequestVm;
import com.spring.boot.social.vm.GeneralResponseVm;

public interface PostService {
    void createPost(PostRequestVm postRequestVm);

    GeneralResponseVm<PostDto> getPostsByAccount(int page, int size);

    GeneralResponseVm<PostDto> getPosts(int page, int size);

    void deletePost(Long id);

    PostDto getPostByCurrentAccount(Long id);

    PostDto getPost(Long id);

    PostDto updatePost(PostRequestVm postRequestVm);

    void incrementReactionCount(Long postId);

    void decrementReactionCount(Long postId);

    void incrementCommentCount(Long postId);

    void decrementCommentCount(Long postId);
}
