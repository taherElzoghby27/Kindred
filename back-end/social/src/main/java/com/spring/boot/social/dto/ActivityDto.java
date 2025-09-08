package com.spring.boot.social.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.social.utils.enums.ActivityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ActivityDto {
    private Long id;
    private String activityMessage;
    @Enumerated(EnumType.STRING)
    private ActivityType activity;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
    private String createdBy;
}
