package com.spring.boot.social.dto.chat;

import com.spring.boot.social.vm.auth.AccountVm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ChatParticipantDto {
    private Long id;
    private AccountVm account;
}
