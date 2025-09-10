package com.spring.boot.social.vm;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Post information for creation and updates")
public class PostRequestVm {

    @Schema(description = "Content of the post", example = "This is my first post!")
    private String content;

    @Schema(description = "Media file (video or photo)")
    private MultipartFile media;
}
