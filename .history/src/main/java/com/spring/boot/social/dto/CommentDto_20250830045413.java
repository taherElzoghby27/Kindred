package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.vm.AccountVm;
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
@Schema(description = "Comment information for creation and updates")
public class CommentDto {
    @Schema(description = "Unique identifier for the comment", example = "1")
    private Long id;
    
    @Schema(description = "Content of the comment", example = "Great post!")
    private String content;
    
    @Schema(description = "Date and time when the comment was created", example = "2024-01-15T10:30:00")
    private LocalDateTime createdDate;
    
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @Schema(description = "Account information of the comment creator")
    private AccountVm account;
    
    @Schema(description = "ID of the post this comment belongs to", example = "1")
    private Long postId;
}
