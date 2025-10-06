package com.spring.boot.social.services.impl;

import com.spring.boot.social.dto.AccountDto;
import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.exceptions.BadRequestException;
import com.spring.boot.social.exceptions.NotFoundResourceException;
import com.spring.boot.social.mappers.PostMapper;
import com.spring.boot.social.entity.Post;
import com.spring.boot.social.entity.security.Account;
import com.spring.boot.social.repositories.PostRepo;
import com.spring.boot.social.repositories.ReactionPostRepo;
import com.spring.boot.social.services.AccountService;
import com.spring.boot.social.services.ActivityService;
import com.spring.boot.social.services.PostService;
import com.spring.boot.social.utils.SecurityUtils;
import com.spring.boot.social.utils.LocalService;
import com.spring.boot.social.utils.enums.ActivityType;
import com.spring.boot.social.vm.PostRequestVm;
import com.spring.boot.social.vm.PostsResponseVm;
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
    private final ReactionPostRepo reactionPostRepo;

    @Override
    public void createPost(PostRequestVm postRequestVm) {
        if (Objects.isNull(postRequestVm.getContent()) && Objects.isNull(postRequestVm.getMedia())) {
            throw new BadRequestException("error.required.one.field.post");
        }
        //get current account
        Account account = accountService.getCurrentAccount();
        Post post = PostMapper.POST_INSTANCE.toPost(postRequestVm);
        if (Objects.nonNull(postRequestVm.getMedia())) {
            post.setMedia(postRequestVm.getMedia().getOriginalFilename());
            LocalService.uploadFile(postRequestVm.getMedia());
        }
        //set account to post
        post.setAccount(account);
        //save post in db
        post = postRepo.save(post);
        //add log
        activityService.logActivity(new RequestActivityVm("Created post " + post.getContent(), ActivityType.POST_CREATED));
    }


    @Override
    public PostsResponseVm getPostsByAccount(int page, int size) {
        //get current account
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        Pageable pageable = getPageable(page, size);
        Page<Post> posts = postRepo.findAllByAccountIdOrderByCreatedDateDesc(pageable, accountDto.getId());
        return getPostsResponseVm(accountDto, posts);
    }

    @Override
    public PostsResponseVm getPosts(int page, int size) {
        Pageable pageable = getPageable(page, size);
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        Page<Post> posts = postRepo.findAllByOrderByCreatedDateDesc(pageable);
        return getPostsResponseVm(accountDto, posts);
    }

    private PostsResponseVm getPostsResponseVm(AccountDto accountDto, Page<Post> posts) {
        updateLikedFields(accountDto, posts.getContent());
        List<PostDto> postsDto = posts.getContent().stream().map(PostMapper.POST_INSTANCE::toPostDto).toList();
        return new PostsResponseVm(postsDto, posts.getNumber() + 1, posts.getSize());
    }

    private void updateLikedFields(AccountDto accountDto, List<Post> posts) {
        //posts liked (ids)
        List<Long> liked = reactionPostRepo.findPostIdsLikedByAccount(accountDto.getId());
        posts.forEach(p -> p.setLiked(liked.contains(p.getId()) ? 1L : 0L));
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
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        Post post = result.get();
        updateLikedFields(accountDto, List.of(post));
        return PostMapper.POST_INSTANCE.toPostDto(post);
    }

    @Override
    public PostDto updatePost(PostRequestVm postRequestVm) {
        if (Objects.isNull(postRequestVm.getId())) {
            throw new BadRequestException("required.id");
        }
        Post post = getPostBasedOnCurrentAccount(postRequestVm.getId());
        if (Objects.isNull(post)) {
            throw new BadRequestException("post.not.found");
        }
        if (postRequestVm.getContent().equals(post.getContent()) && post.getMedia().equals(postRequestVm.getMedia().getOriginalFilename())) {
            throw new BadRequestException("no.changes");
        }
        if (Objects.nonNull(postRequestVm.getMedia())) {
            post.setMedia(postRequestVm.getMedia().getOriginalFilename());
        }
        if (Objects.nonNull(postRequestVm.getContent())) {
            post.setContent(postRequestVm.getContent());
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
