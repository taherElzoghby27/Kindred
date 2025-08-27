package com.spring.boot.social.dto.friendship;

import com.spring.boot.social.utils.enums.FriendStatusEnum;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FriendStatusDto {
    private Long id;
    @Enumerated(EnumType.STRING)
    private FriendStatusEnum status;
}
