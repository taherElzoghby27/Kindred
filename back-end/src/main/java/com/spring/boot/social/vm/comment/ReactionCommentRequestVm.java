package com.spring.boot.social.vm.comment;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.utils.enums.ReactionType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ReactionCommentRequestVm {
    @NotNull(message = "id.comment.not_null")
    @JsonProperty("comment_id")
    private Long commentId;

    @NotNull(message = "type.not.null")
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "reaction_type")
    private ReactionType reactionType;
}
