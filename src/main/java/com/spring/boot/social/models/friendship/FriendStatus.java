package com.spring.boot.social.models.friendship;

import com.spring.boot.social.models.BaseEntity;
import com.spring.boot.social.utils.enums.FriendshipEnum;
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
public class FriendStatus extends BaseEntity<String> {
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendshipEnum status;
    @OneToMany(mappedBy = "status")
    private List<FriendshipStatus> friendshipStatuses;
}
