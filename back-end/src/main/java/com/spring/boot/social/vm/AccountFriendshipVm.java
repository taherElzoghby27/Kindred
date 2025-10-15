package com.spring.boot.social.vm;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import jakarta.persistence.Column;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountFriendshipVm {
    @JsonProperty("ac1_id")
    private Long accountId;
    @JsonProperty("ac1_username")
    private String accountUsername;
    @JsonProperty("ac2_id")
    private Long friendId;
    @JsonProperty("ac2_username")
    private String friendUsername;
    @JsonProperty("friendship_id")
    private Long friendshipId;
    @JsonProperty("status")
    private FriendStatusEnum status;
}
