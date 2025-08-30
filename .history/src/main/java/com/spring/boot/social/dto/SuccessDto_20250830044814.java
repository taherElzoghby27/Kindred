package com.spring.boot.social.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Generic success response wrapper")
public class SuccessDto<T> {
    @Schema(description = "Response data", example = "Any type of response data")
    private T data;
}
