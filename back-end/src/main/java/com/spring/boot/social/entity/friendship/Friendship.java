package com.spring.boot.social.entity.friendship;

import com.spring.boot.social.entity.BaseEntity;
import com.spring.boot.social.entity.Account;
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
                        "friend_id"
                }
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Friendship extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account account;
    @ManyToOne
    @JoinColumn(name = "friend_id", nullable = false)
    private Account friend;
    @OneToOne(mappedBy = "friendship")
    private FriendshipStatus friendshipStatus;
}
