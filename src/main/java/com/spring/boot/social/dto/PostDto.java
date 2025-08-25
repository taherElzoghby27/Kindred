package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.vm.AccountVm;
import com.spring.boot.social.vm.CommentResponseVm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PostDto {
    private Long id;
    private String content;
    private String media;
    private LocalDateTime createdDate;
    private List<CommentResponseVm> comments;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AccountVm account;
}
