package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.mappers.AccountMapper;
import com.spring.boot.social.mappers.PostMapper;
import com.spring.boot.social.models.Post;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.PostRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.utils.SecurityUtils;
import com.spring.boot.social.vm.PostsVmResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final AccountService accountService;

    @Override
    public PostDto createPost(PostDto postDto) {
        if (Objects.isNull(postDto.getContent()) && Objects.isNull(postDto.getMedia())
            || (postDto.getContent().isEmpty() && postDto.getMedia().isEmpty())) {
            throw new BadRequestException("error.required.one.field.post");
        }
        //get current account
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        accountDto = accountService.getAccountById(accountDto.getId());
        Account account = AccountMapper.ACCOUNT_MAPPER.toAccount(accountDto);
        Post post = PostMapper.POST_INSTANCE.toPost(postDto);
        //set account to post
        post.setAccount(account);
        //save post in db
        post = postRepo.save(post);
        postDto = PostMapper.POST_INSTANCE.toPostDto(post);
        return postDto;
    }

    @Override
    public PostsVmResponse getPosts(int page, int size) {
        Pageable pageable = getPageable(page, size);
        Page<Post> posts = postRepo.findAllOrderByCreatedBy(pageable);
        List<PostDto> postsDto = posts.getContent().stream().map(PostMapper.POST_INSTANCE::toPostDto).toList();
        return new PostsVmResponse(
               postsDto,
               posts.getTotalPages(),
               posts.getSize()
        );
    }

    private Pageable getPageable(int page, int size) {
        if (page < 1) {
            throw new BadRequestException("error.min.one.page");
        }
        return PageRequest.of(page - 1, size);
    }

    @Override
    public void deletePost(Long id) {

    }

    @Override
    public PostDto getPost(Long id) {
        return null;
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        return null;
    }
}
