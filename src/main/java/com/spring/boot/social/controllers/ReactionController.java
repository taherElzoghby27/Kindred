package com.spring.boot.social.controllers;

import com.spring.boot.social.dto.SuccessDto;
import com.spring.boot.social.services.ReactionPostService;
import com.spring.boot.social.vm.ReactionRequestVm;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RequestMapping("/reaction")
@RestController
@RequiredArgsConstructor
public class ReactionController {
    private final ReactionPostService reactionPostService;


    @PostMapping("/reaction-request")
    @PreAuthorize("isAuthenticated()")
    public SuccessDto<ResponseEntity<String>> reactionRequest(@Valid @RequestBody ReactionRequestVm reactionRequestVm) {
        reactionPostService.reactionRequest(reactionRequestVm);
        return new SuccessDto<>(ResponseEntity.created(URI.create("/reaction-request")).body("Success"));
    }

    @DeleteMapping("/delete-react")
    @PreAuthorize("isAuthenticated()")
    public SuccessDto<ResponseEntity<String>> deleteReact(@Valid @RequestBody ReactionRequestVm reactionRequestVm) {
        reactionPostService.removeReaction(reactionRequestVm);
        return new SuccessDto<>(ResponseEntity.ok("Success"));
    }
}
