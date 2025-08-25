package com.spring.boot.social.controllers;
import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.CommentService;
import com.spring.boot.social.vm.CommentRequestVm;
import com.spring.boot.social.vm.CommentResponseVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/comments")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;


    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create-comment")
    public ResponseEntity<CommentResponseVm> createComment(@Valid @RequestBody CommentRequestVm commentRequestVm) {
        return ResponseEntity.created(URI.create("/create-comment")).body(commentService.createComment(commentRequestVm));
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/update-comment")
    public ResponseEntity<CommentResponseVm> updateComment(@Valid @RequestBody CommentRequestVm commentRequestVm) {
        return ResponseEntity.ok(commentService.updateComment(commentRequestVm));
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/delete-comment")
    public ResponseEntity<SuccessDto> deleteComment(@Valid @RequestParam Long commentId) {
        commentService.deleteComment(commentId);
        return ResponseEntity.ok(new SuccessDto<>( "Successfully Deleted"));
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/get-comments")
    public ResponseEntity<List<CommentResponseVm>> getComments(@Valid @RequestParam Long postId) {
        return ResponseEntity.ok(commentService.getCommentsByPostId(postId));
    }
}
