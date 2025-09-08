package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.PostDto;
import com.spring.boot.social.models.Post;
import com.spring.boot.social.models.security.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

@Mapper
public interface PostMapper {
    PostMapper POST_INSTANCE = Mappers.getMapper(PostMapper.class);

    Post toPost(PostDto postDto);

    PostDto toPostDto(Post post);

    @Named("postToId")
    default Long postToId(Post post) {
        return post == null ? null : post.getId();
    }
}
