package com.spring.boot.social.mappers;

import com.spring.boot.social.dto.ReactionDto;
import com.spring.boot.social.entity.PostReactionAccount;
import com.spring.boot.social.entity.Reaction;
import com.spring.boot.social.vm.PostReactionAccountVm;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ReactionMapper {
    ReactionMapper INSTANCE = Mappers.getMapper(ReactionMapper.class);

    ReactionDto toReactionDto(Reaction reaction);

    Reaction toReaction(ReactionDto reactionDto);

    PostReactionAccountVm toPostReactionAccountVm(PostReactionAccount postReactionAccount);
}
