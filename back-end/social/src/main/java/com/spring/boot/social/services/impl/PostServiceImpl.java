package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.PostMapper;
import com.spring.boot.social.models.Post;
import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.repositories.PostRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.utils.SecurityUtils;
import com.spring.boot.social.utils.enums.ActivityType;
import com.spring.boot.social.vm.PostsVmResponse;
import com.spring.boot.social.vm.RequestActivityVm;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {
    private final PostRepo postRepo;
    private final AccountService accountService;
    private final ActivityService activityService;

    @Override
    public PostDto createPost(PostDto postDto) {
        if (Objects.isNull(postDto.getContent()) && Objects.isNull(postDto.getMedia()) || (postDto.getContent().isEmpty() && postDto.getMedia().isEmpty())) {
            throw new BadRequestException("error.required.one.field.post");
        }
        //get current account
        Account account = accountService.getCurrentAccount();
        Post post = PostMapper.POST_INSTANCE.toPost(postDto);
        //set account to post
        post.setAccount(account);
        //save post in db
        post = postRepo.save(post);
        postDto = PostMapper.POST_INSTANCE.toPostDto(post);
        //add log
        activityService.logActivity(new RequestActivityVm("Created post " + post.getContent(), ActivityType.POST_CREATED));
        return postDto;
    }

    @Override
    public PostsVmResponse getPostsByAccount(int page, int size) {
        //get current account
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        Pageable pageable = getPageable(page, size);
        Page<Post> posts = postRepo.findAllByAccountIdOrderByCreatedByAsc(pageable, accountDto.getId());
        List<PostDto> postsDto = posts.getContent().stream().map(PostMapper.POST_INSTANCE::toPostDto).toList();
        return new PostsVmResponse(postsDto, posts.getNumber() + 1, posts.getSize());
    }

    @Override
    public PostsVmResponse getPosts(int page, int size) {
        Pageable pageable = getPageable(page, size);
        Page<Post> posts = postRepo.findAllByOrderByCreatedByAsc(pageable);
        List<PostDto> postsDto = posts.getContent().stream().map(PostMapper.POST_INSTANCE::toPostDto).toList();
        return new PostsVmResponse(postsDto, posts.getNumber() + 1, posts.getSize());
    }

    private Pageable getPageable(int page, int size) {
        if (page < 1) {
            throw new BadRequestException("error.min.one.page");
        }
        return PageRequest.of(page - 1, size);
    }

    @Override
    public void deletePost(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("required.id");
        }
        Post post = getPostBasedOnCurrentAccount(id);
        if (Objects.isNull(post)) {
            throw new NotFoundResourceException("post.not.found");
        }
        postRepo.deleteById(id);
    }

    private Post getPostBasedOnCurrentAccount(Long id) {
        //get current account
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        return getPostByIdAndAccountId(id, accountDto.getId());
    }

    private Post getPostByIdAndAccountId(Long id, Long AccountId) {
        return postRepo.findPostsByIdAndAccountId(id, AccountId);
    }

    @Override
    public PostDto getPostByCurrentAccount(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("required.id");
        }
        Post post = getPostBasedOnCurrentAccount(id);
        if (Objects.isNull(post)) {
            throw new NotFoundResourceException("post.not.found");
        }
        return PostMapper.POST_INSTANCE.toPostDto(post);
    }

    @Override
    public PostDto getPost(Long id) {
        if (Objects.isNull(id)) {
            throw new BadRequestException("required.id");
        }
        Optional<Post> result = postRepo.findById(id);
        if (result.isEmpty()) {
            throw new NotFoundResourceException("post.not.found");
        }
        return PostMapper.POST_INSTANCE.toPostDto(result.get());
    }

    @Override
    public PostDto updatePost(PostDto postDto) {
        if (Objects.isNull(postDto.getId())) {
            throw new BadRequestException("required.id");
        }
        Post post = getPostBasedOnCurrentAccount(postDto.getId());
        if (Objects.isNull(post)) {
            throw new BadRequestException("post.not.found");
        }
        if (postDto.getContent().equals(post.getContent()) && postDto.getMedia().equals(post.getMedia())) {
            throw new BadRequestException("no.changes");
        }
        if (Objects.nonNull(postDto.getMedia())) {
            post.setMedia(postDto.getMedia());
        }
        if (Objects.nonNull(postDto.getContent())) {
            post.setContent(postDto.getContent());
        }
        post = postRepo.save(post);
        return PostMapper.POST_INSTANCE.toPostDto(post);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void incrementReactionCount(Long postId) {
        postRepo.incrementReactionCount(postId);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void decrementReactionCount(Long postId) {
        postRepo.decrementReactionCount(postId);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void incrementCommentCount(Long postId) {
        postRepo.incrementCommentsCount(postId);
    }

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void decrementCommentCount(Long postId) {
        postRepo.decrementCommentsCount(postId);
    }
}
