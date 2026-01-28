package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.vm.auth.AccountVm;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Post information ")
public class PostDto {
    @Schema(description = "Unique identifier for the post", example = "1")
    private Long id;
    private Long liked;
    @Schema(description = "Content of the post", example = "This is my first post!")
    private String content;

    @Schema(description = "Media URL associated with the post", example = "https://example.com/image.jpg")
    private String media;

    @Schema(description = "Number of reactions on the post", example = "42")
    private Long reactionsCount;

    @Schema(description = "Number of comments on the post", example = "15")
    private Long commentsCount;

    @Schema(description = "Date and time when the post was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdDate;

    //private List<CommentResponseVm> comments;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Account information of the post creator")
    private AccountDto account;
}
