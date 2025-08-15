package com.spring.boot.social.models;

import com.spring.boot.social.utils.enums.FriendshipEnum;
import jakarta.persistence.*;
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
public class FriendshipStatus extends BaseEntity<String> {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendshipEnum status;
    @OneToOne(mappedBy = "status")
    private Friendship friendship;
}
