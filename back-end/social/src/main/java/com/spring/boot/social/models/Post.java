package com.spring.boot.social.models;

import com.spring.boot.social.models.security.Account;
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
public class Post extends BaseEntity<String> {
    private String content;
    private String media;
    private Long reactionsCount = 0L;
    private Long commentsCount = 0L;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Account account;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReactionAccount> postsReactionsAccounts;
}
