package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.spring.boot.social.utils.enums.ActivityType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RequestActivityVm {
    private String activityMessage;
    @Enumerated(EnumType.STRING)
    private ActivityType activity;
}
