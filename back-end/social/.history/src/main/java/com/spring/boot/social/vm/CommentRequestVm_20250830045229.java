package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Comment request information for creation and updates")
public class CommentRequestVm {
    @Schema(description = "Unique identifier for the comment (null for new comments)", example = "1")
    private Long id;
    
    @NotEmpty(message = "empty.content")
    @Schema(description = "Content of the comment", example = "Great post!", required = true)
    private String content;
    
    @NotNull(message = "empty.post_id")
    @Schema(description = "ID of the post this comment belongs to", example = "1", required = true)
    private Long postId;
}
