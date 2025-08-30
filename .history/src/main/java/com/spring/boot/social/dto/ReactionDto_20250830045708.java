package com.spring.boot.social.dto;

import com.spring.boot.social.utils.enums.ReactionType;
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
public class ReactionDto {
    private Long id;
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
