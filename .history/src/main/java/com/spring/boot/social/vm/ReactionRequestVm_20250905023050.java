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
public class ReactionRequestVm {
    @NotNull(message = "post_id.comment.not_null")
    @JsonProperty(value = "post_id")
    private Long postId;
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
