package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.vm.AccountVm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {
    private Long id;
    private String content;
    private String media;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private AccountVm account;
}
