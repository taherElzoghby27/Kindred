package com.spring.boot.social.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Post information for creation and updates")
public class PostRequestVm {
    private Long id;
    @Schema(description = "Content of the post", example = "This is my first post!")
    private String content;

    @Schema(description = "Media file name (video or photo)")
    private String media;
}
