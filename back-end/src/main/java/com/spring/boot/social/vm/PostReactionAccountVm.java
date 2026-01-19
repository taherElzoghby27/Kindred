package com.spring.boot.social.vm;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.vm.auth.AccountVm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostReactionAccountVm {
    private PostDto post;
    private ReactionDto reaction;
    private AccountVm account;
}
