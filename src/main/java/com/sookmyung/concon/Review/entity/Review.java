package com.sookmyung.concon.Review.entity;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    // TODO : Coupon -> Item 으로 변경할 것
    @OneToOne(fetch = FetchType.LAZY)
    private Coupon coupon;

    private double score;

    private String content;

    public void update(double score, String content) {
        this.score = score;
        this.content = content;
    }
}
