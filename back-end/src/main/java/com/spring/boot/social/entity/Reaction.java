package com.spring.boot.social.entity;

import com.spring.boot.social.entity.comment.CommentReactionAccount;
import com.spring.boot.social.entity.post.PostReactionAccount;
import com.spring.boot.social.utils.enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Reaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToMany(mappedBy = "reaction")
    private List<PostReactionAccount> postsReactionsAccounts;
    @OneToMany(mappedBy = "reaction")
    private List<CommentReactionAccount> commentsReactionAccounts;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
