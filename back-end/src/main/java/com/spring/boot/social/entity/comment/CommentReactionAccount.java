package com.spring.boot.social.entity.comment;

import com.spring.boot.social.entity.Account;
import com.spring.boot.social.entity.BaseEntity;
import com.spring.boot.social.entity.Reaction;
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
                        "comment_id"
                }
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class CommentReactionAccount extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "comment_id", nullable = false)
    private Comment comment;
    @ManyToOne
    @JoinColumn(name = "reaction_id", nullable = false)
    private Reaction reaction;
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
}
