package com.spring.boot.social.vm.chat;

import com.spring.boot.social.vm.auth.AccountVm;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageResponseVm {
    private Long id;
    private String text;
    private boolean seen;
    private AccountVm account;
    private LocalDateTime createdDate;
    private LocalDateTime updatedDate;
}
