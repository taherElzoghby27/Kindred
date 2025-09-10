package com.spring.boot.social.dto;

import com.spring.boot.social.utils.enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Post reaction account information")
public class PostReactionAccountDto {
    @Schema(description = "Unique identifier for the post reaction account", example = "1")
    private Long id;
    
    @Schema(description = "Type of reaction (LIKE, LOVE, HAHA, WOW, SAD, ANGRY)", example = "LIKE")
    private ReactionType reactionType;
}
