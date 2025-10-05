package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.entity.Post;
import com.spring.boot.social.vm.PostRequestVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper POST_INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(PostDto postDto);

    @Mapping(source = "media", target = "media", ignore = true)
    Post toPost(PostRequestVm postRequestVm);

    PostDto toPostDto(Post post);

    @Named("postToId")
    default Long postToId(Post post) {
        return post == null ? null : post.getId();
    }
}
