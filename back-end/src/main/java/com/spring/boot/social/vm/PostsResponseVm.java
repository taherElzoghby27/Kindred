package com.spring.boot.social.vm;

import com.spring.boot.social.dto.PostDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
public class PostsResponseVm {
    private List<PostDto> posts;
    private int page;
    private int size;
}
