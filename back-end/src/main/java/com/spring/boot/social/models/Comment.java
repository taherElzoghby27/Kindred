package com.spring.boot.social.models;

import com.spring.boot.social.models.security.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "comment_post", schema = "kindred")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Comment extends BaseEntity<String> {
    @NotEmpty
    private String content;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Post post;
    @JoinColumn(nullable = false)
    @ManyToOne
    private Account account;
}
