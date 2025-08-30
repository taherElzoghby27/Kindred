package com.spring.boot.social.vm;

import com.spring.boot.social.dto.PostDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Posts response with pagination information")
public class PostsVmResponse {
    @Schema(description = "List of posts", example = "[]")
    private List<PostDto> posts;
    
    @Schema(description = "Total number of posts", example = "100")
    private Long totalPosts;
}
