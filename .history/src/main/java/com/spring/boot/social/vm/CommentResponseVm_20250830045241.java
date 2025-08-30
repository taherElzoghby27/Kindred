package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Comment response information")
public class CommentResponseVm {
    @Schema(description = "Unique identifier for the comment", example = "1")
    private Long id;
    
    @Schema(description = "Content of the comment", example = "Great post!")
    private String content;
    
    @Schema(description = "Date and time when the comment was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdDate;
}