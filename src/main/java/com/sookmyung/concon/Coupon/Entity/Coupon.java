package com.sookmyung.concon.Coupon.Entity;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class Coupon {

    @Id
    @Column(name = "coupon_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "item_id")
    private Item item;

    // 바코드 + 바코드 이미지 url
    private String barcode;
    private String barcodeImageFileName;
    private LocalDateTime barcodeImageCreatedDate;

    // 아이템 사진
    private String itemImagePath;

    // 쿠폰 사진
    private String imageFileName;
    private LocalDateTime imageCreateDate;

    private Double itemPrice;
    private Double remainingPrice;

    public void changeRemainingPrice(Double remainingPrice) {
        this.remainingPrice = remainingPrice;
    }

    private LocalDate expirationDate;

    private LocalDate usedDate;

    public void useCoupon(LocalDate useDate) {
        this.usedDate = useDate;
    }

    @Setter
    private boolean buyFlag;   // 구매 여부

    private String memo;

    public void changeUser(User user) {
        this.user = user;
    }

}