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
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    public void createPost(PostRequestVm postRequestVm) {
        if (Objects.isNull(postRequestVm.getContent()) && Objects.isNull(postRequestVm.getMedia()) || (postRequestVm.getContent().isEmpty() && postRequestVm.getMedia().isEmpty())) {
            throw new BadRequestException("error.required.one.field.post");
        }
        //get current account
        Account account = accountService.getCurrentAccount();
        Post post = PostMapper.POST_INSTANCE.toPost(postRequestVm);
        System.out.println(uploadFile(postRequestVm.getMedia()));
        post.setMedia(postRequestVm.getMedia().getOriginalFilename());
        //set account to post
        post.setAccount(account);
        //save post in db
        post = postRepo.save(post);
        //add log
        activityService.logActivity(new RequestActivityVm("Created post " + post.getContent(), ActivityType.POST_CREATED));
    }

    private String uploadFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new BadRequestException("not.found.media");
        }
        try {
            final String UPLOAD_DIR = "uploads/";
            // 1.make sure upload directory exist
            File uploadsDir = new File(UPLOAD_DIR);
            if (!uploadsDir.exists()) {
                uploadsDir.mkdir();
            }
            // 2. Build the path: uploads/filename.ext
            Path filePath = Paths.get(UPLOAD_DIR, file.getOriginalFilename());
            // 3. Save file to the path
            Files.write(filePath, file.getBytes());
            return file.getOriginalFilename();
        } catch (IOException e) {
            throw new RuntimeException("File upload failed: " + e.getMessage());
        }
    }

    @Override
    public PostsResponseVm getPostsByAccount(int page, int size) {
        //get current account
        AccountDto accountDto = SecurityUtils.getCurrentAccount();
        Pageable pageable = getPageable(page, size);
        Page<Post> posts = postRepo.findAllByAccountIdOrderByCreatedByAsc(pageable, accountDto.getId());
        List<PostDto> postsDto = posts.getContent().stream().map(PostMapper.POST_INSTANCE::toPostDto).toList();
        return new PostsResponseVm(postsDto, posts.getNumber() + 1, posts.getSize());
    }

    @Override
    public PostsResponseVm getPosts(int page, int size) {
        Pageable pageable = getPageable(page, size);
        Page<Post> posts = postRepo.findAllByOrderByCreatedByAsc(pageable);
        List<PostDto> postsDto = posts.getContent().stream().map(PostMapper.POST_INSTANCE::toPostDto).toList();
        return new PostsResponseVm(postsDto, posts.getNumber() + 1, posts.getSize());
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
