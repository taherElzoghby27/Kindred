package com.spring.boot.social.dto.friendship;

import com.spring.boot.social.dto.AccountDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendShipDto {
    private Long id;
    private AccountDto account;
    private AccountDto friend;
}