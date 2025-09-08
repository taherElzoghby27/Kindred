package com.spring.boot.social.vm;

import com.spring.boot.social.utils.enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Reaction request information for posts")
public class ReactionRequestVm {
    @NotNull(message = "empty.post_id")
    @Schema(description = "ID of the post to react to", example = "1", required = true)
    private Long postId;
    
    @NotNull(message = "empty.reaction_type")
    @Schema(description = "Type of reaction (LIKE, LOVE, HAHA, WOW, SAD, ANGRY)", example = "LIKE", required = true)
    private ReactionType reactionType;
}
