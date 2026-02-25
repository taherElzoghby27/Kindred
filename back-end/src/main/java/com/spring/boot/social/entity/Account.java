package com.spring.boot.social.entity;

import com.spring.boot.social.entity.chat.ChatParticipant;
import com.spring.boot.social.entity.comment.Comment;
import com.spring.boot.social.entity.comment.CommentReactionAccount;
import com.spring.boot.social.entity.post.Post;
import com.spring.boot.social.entity.post.PostReactionAccount;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;
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
    @Column()
    private String lastName;
    @Min(value = 16, message = "error.age")
    @Column()
    private Long age;
    @Column()
    private String phoneNumber;
    @Column()
    private String address;
    @Column()
    private String fullName;
    @Column()
    private LocalDateTime birthday;
    @Column()
    private String bio;
    @Column()
    private String profilePictureUrl;
    //0 or 1
    private Long enabled;
    @OneToMany(mappedBy = "account")
    private List<Post> posts;
    @OneToMany(mappedBy = "account")
    private List<Comment> comments;
    @OneToMany(mappedBy = "account")
    private List<Activity> activities;
    @OneToMany(mappedBy = "account")
    private List<PostReactionAccount> postsReactionsAccounts;
    @OneToMany(mappedBy = "account")
    private List<CommentReactionAccount> commentsReactionAccounts;
    @OneToMany(mappedBy = "recipient")
    private List<Notification> notifications;
    @OneToMany(mappedBy = "account")
    private List<ChatParticipant> chatParticipants;
}
