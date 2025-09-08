package com.spring.boot.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class PostReactionAccountDto {
    private ReactionDto reaction;
    private AccountDto account;
}
