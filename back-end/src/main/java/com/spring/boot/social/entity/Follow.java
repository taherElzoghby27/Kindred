package com.spring.boot.social.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(
        uniqueConstraints = @UniqueConstraint(
                columnNames = {
                        "follower_id",
                        "followee_id"
                }
        )
)
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Follow extends BaseEntity<String> {
    @ManyToOne
    @JoinColumn(name = "follower_id", nullable = false)
    private Account follower;
    @ManyToOne
    @JoinColumn(name = "followee_id", nullable = false)
    private Account followee;
}
