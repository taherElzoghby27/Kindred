package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
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
    private String createdBy;
    private LocalDateTime updatedDate;
    private LocalDateTime createdDate;
}