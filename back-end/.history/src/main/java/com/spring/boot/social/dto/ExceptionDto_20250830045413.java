package com.spring.boot.social.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Exception response information")
public class ExceptionDto {
    @Schema(description = "Error message", example = "Something went wrong")
    private String message;
    
    @Schema(description = "HTTP status code", example = "500")
    private String status;
    
    @Schema(description = "Error timestamp", example = "2024-01-15T10:30:00")
    private String timestamp;
}
