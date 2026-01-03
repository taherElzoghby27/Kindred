package com.spring.boot.social.entity;

import com.spring.boot.social.entity.security.Account;
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
public class Post extends BaseEntity<String> {
    private String content;
    private String media;
    private Long reactionsCount = 0L;
    private Long commentsCount = 0L;
    @Transient
    private Long liked;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Account account;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comment> comments;
    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<PostReactionAccount> postsReactionsAccounts;
}
