package com.spring.boot.social.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
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
public class PostReactionAccount extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "post_id", nullable = false)
    private Post post;
    @ManyToOne
    @JoinColumn(name = "reaction_id", nullable = false)
    private Reaction reaction;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
