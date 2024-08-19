package com.sookmyung.concon.Coupon.Entity;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "item_id")
    private Item item;

    private String barcode;

    private String barcodeImageFileName;

    private String imageFileName;

    private String name;

    private Double price;

    private LocalDate expirationDate;

    private LocalDate usedDate;

    private String category;

    @Setter
    private boolean buyFlag;   // 구매 여부

}
