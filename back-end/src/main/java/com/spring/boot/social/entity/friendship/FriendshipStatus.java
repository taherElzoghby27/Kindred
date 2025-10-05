package com.spring.boot.social.entity.friendship;

import com.spring.boot.social.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        schema = "kindred",
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "friend_ship_id",
                        "status_id"
                }
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class FriendshipStatus extends BaseEntity<String> {
    @OneToOne
    @JoinColumn(nullable = false, name = "friend_ship_id")
    private Friendship friendship;

    @ManyToOne
    @JoinColumn(nullable = false, name = "status_id")
    private FriendStatus status;
}
