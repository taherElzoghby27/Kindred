package com.spring.boot.social.models;

import com.spring.boot.social.models.security.Account;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(schema = "hr")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Comment extends BaseEntity<String> {
    @NotEmpty
    private String content;
    @JoinColumn(unique = true)
    @ManyToOne
    private Post post;
    @ManyToOne
    private Account account;
}
