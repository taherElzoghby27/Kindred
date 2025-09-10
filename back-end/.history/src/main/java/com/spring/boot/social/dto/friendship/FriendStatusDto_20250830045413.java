package com.spring.boot.social.dto.friendship;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Friend status information")
public class FriendStatusDto {
    @Schema(description = "Unique identifier for the friend status", example = "1")
    private Long id;
    
    @Schema(description = "Current status of the friendship (PENDING, ACCEPTED, REJECTED)", example = "PENDING")
    private String status;
    
    @Schema(description = "ID of the friendship this status belongs to", example = "1")
    private Long friendshipId;
}
