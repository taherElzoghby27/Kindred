package com.spring.boot.social.models.security;

import com.spring.boot.social.models.Activity;
import com.spring.boot.social.models.BaseEntity;
import com.spring.boot.social.models.Comment;
import com.spring.boot.social.models.Post;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(schema = "hr")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Account extends BaseEntity<String> {
    @Column(nullable = false, unique = true)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountDetails accountDetails;
    @OneToMany(mappedBy = "account")
    private List<Post> posts;
    @OneToMany(mappedBy = "account")
    private List<Comment> comments;
    @OneToMany
    private List<Activity> activities;
}
