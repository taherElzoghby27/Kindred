package com.spring.boot.social.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendshipStatusDto {
    private FriendShipDto friendship;
    private FriendStatusDto status;
}
