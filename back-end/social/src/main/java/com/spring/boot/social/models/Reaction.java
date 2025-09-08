package com.spring.boot.social.models;

import com.spring.boot.social.utils.enums.ReactionType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "kindred")
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
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ReactionType reactionType;
}
