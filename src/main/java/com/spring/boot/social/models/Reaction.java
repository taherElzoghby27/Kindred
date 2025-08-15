package com.spring.boot.social.models;

import com.spring.boot.social.models.security.Account;
import com.spring.boot.social.utils.enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        schema = "kindred",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "account_id",
                        "post_id"
                }
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reaction extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
