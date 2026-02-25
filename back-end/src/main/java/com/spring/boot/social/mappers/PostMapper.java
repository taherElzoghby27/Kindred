package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.entity.post.Post;
import com.spring.boot.social.vm.PostRequestVm;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper POST_INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(PostDto postDto);

    Post toPost(PostRequestVm postRequestVm);

    PostDto toPostDto(Post post);

    @Named("postToId")
    default Long postToId(Post post) {
        return post == null ? null : post.getId();
    }
}
