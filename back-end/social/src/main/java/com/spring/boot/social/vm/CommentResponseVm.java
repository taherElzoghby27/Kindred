package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CommentResponseVm {
    private Long id;
    private String content;
    @JsonProperty("post_id")
    private Long postId;
    private String createdBy;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
}