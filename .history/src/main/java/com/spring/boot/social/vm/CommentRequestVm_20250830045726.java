package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
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
public class CommentRequestVm {
    private Long id;
    @NotEmpty(message = "content.comment")
    private String content;
    @NotNull(message = "post_id.comment.not_null")
    @JsonProperty("post_id")
    private Long postId;
}
