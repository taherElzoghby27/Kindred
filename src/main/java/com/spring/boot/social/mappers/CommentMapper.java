package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.CommentDto;
import com.spring.boot.social.models.Comment;
import com.spring.boot.social.vm.CommentRequestVm;
import com.spring.boot.social.vm.CommentResponseVm;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CommentMapper {
    CommentMapper COMMENT_MAPPER = Mappers.getMapper(CommentMapper.class);

    CommentDto toCommentDto(Comment comment);

    CommentResponseVm toCommentResponseVm(Comment comment);

    @Mapping(source = "postId", target = "post", ignore = true)
    Comment toComment(CommentRequestVm commentRequestVm);
}
