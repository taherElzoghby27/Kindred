package com.spring.boot.social.entity.friendship;

import com.spring.boot.social.utils.enums.FriendStatusEnum;
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
public class FriendStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private FriendStatusEnum status;
    @OneToMany(mappedBy = "status")
    private List<FriendshipStatus> friendshipStatuses;
}
