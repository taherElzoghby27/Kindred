package com.spring.boot.social.entity.security;

import com.spring.boot.social.entity.*;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(uniqueConstraints = @UniqueConstraint(columnNames = {"username", "email"}))
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Account extends BaseEntity<String> {
    @Size(min = 12, max = 50, message = "length.username")
    @Column(nullable = false, unique = true, length = 50)
    private String username;
    @Column(nullable = false, unique = true)
    private String email;
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[^a-zA-Z\\d]).{7,}$", message = "error.password")
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    //0 or 1
    private Long enabled;
    @OneToOne(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private AccountDetails accountDetails;
    @OneToMany(mappedBy = "account")
    private List<Post> posts;
    @OneToMany(mappedBy = "account")
    private List<Comment> comments;
    @OneToMany(mappedBy = "account")
    private List<Activity> activities;
    @OneToMany(mappedBy = "account")
    private List<PostReactionAccount> postsReactionsAccounts;
}
