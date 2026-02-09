package com.spring.boot.social.dto.chat;
import com.spring.boot.social.vm.auth.AccountVm;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class MessageDto {
    private Long id;
    private String text;
    private boolean seen;
    private ChatDto chat;
    private AccountVm account;
    private AccountVm receiver;
}
