package com.spring.boot.social.dto.friendship;

import com.spring.boot.social.dto.AccountDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "Friendship relationship information")
public class FriendShipDto {
    @Schema(description = "Unique identifier for the friendship", example = "1")
    private Long id;
    
    @Schema(description = "ID of the first account in the friendship", example = "1")
    private Long accountId;
    
    @Schema(description = "ID of the second account in the friendship", example = "2")
    private Long friendId;
}
