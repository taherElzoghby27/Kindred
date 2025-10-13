package com.spring.boot.social.vm;
import com.spring.boot.social.utils.enums.FriendStatusEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class AccountFriendshipVm {
    private AccountVm account;
    private Long friendId;
    private FriendStatusEnum status;
}
