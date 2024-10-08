package com.sookmyung.concon.Order.entity;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Orders {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    // 아이템 이미지
    // 자주 조회되므로 필드로 저장
    private String itemPhotoUrl;

    @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller_id")
    private User seller;

    private LocalDate createdDate;

    @Setter
    private LocalDate transactionDate;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    public Item getItem() {
        return coupon.getItem();
    }

    public void updateStatus(OrderStatus status) {
        this.status = status;
    }

//    public void update(String title, String content, double price) {
//        this.title = title;
//        this.content = content;
//        this.price = price;
//    }
}
