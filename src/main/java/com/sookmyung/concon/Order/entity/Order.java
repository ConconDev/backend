package com.sookmyung.concon.Order.entity;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.User.Entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "order_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "buyer_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "seller_id")
    private User seller;

    private String title;
    private String content;
    private int price;
    private LocalDate created_date;
    private LocalDate transaction_date;

    private OrderStatus statue;

    public void updateStatus(OrderStatus statue) {
        this.statue = statue;
    }
}
