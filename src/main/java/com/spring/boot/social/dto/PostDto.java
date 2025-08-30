package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.vm.AccountVm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private String media;
    private Long reactionsCount;
    private Long commentsCount;
    private LocalDateTime createdDate;
    //private List<CommentResponseVm> comments;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AccountVm account;
}
