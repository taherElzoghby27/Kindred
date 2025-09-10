package com.spring.boot.social.dto;

import com.spring.boot.social.utils.enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Reaction information for posts")
public class ReactionDto {
    @Schema(description = "Unique identifier for the reaction", example = "1")
    private Long id;
    
    @Schema(description = "Type of reaction (LIKE, LOVE, HAHA, WOW, SAD, ANGRY)", example = "LIKE")
    private ReactionType reactionType;
    
    @Schema(description = "ID of the post this reaction belongs to", example = "1")
    private Long postId;
    
    @Schema(description = "ID of the account that created this reaction", example = "1")
    private Long accountId;
}
