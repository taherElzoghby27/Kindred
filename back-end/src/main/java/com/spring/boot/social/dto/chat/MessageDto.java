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
public class MessageDto {
    private Long id;
    private String text;
    private boolean seen;
    private ChatDto chat;
    private AccountVm account;
    private AccountVm receiver;
}
