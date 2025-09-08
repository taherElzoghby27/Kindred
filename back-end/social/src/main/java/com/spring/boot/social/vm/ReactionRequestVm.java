package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.utils.enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Request model for creating a reaction on a post")
public class ReactionRequestVm {
    @NotNull(message = "post_id.comment.not_null")
    @JsonProperty(value = "post_id")
    @Schema(description = "ID of the post to react to", example = "123", requiredMode = Schema.RequiredMode.REQUIRED)
    private Long postId;

    @Enumerated(EnumType.STRING)
    @Schema(description = "Type of reaction to add to the post", example = "LIKE", requiredMode = Schema.RequiredMode.REQUIRED)
    private ReactionType reactionType;
}
