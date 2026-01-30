package com.spring.boot.social.vm.chat;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MessageRequestVm {
    @NotEmpty(message = "text.not_empty")
    private String text;
    @JsonProperty("chat_id")
    private Long chatId;
    @NotNull(message = "receiver_id.not_null")
    @JsonProperty("receiver_id")
    private Long receiverId;
}
