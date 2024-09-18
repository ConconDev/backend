package com.sookmyung.concon.User.Entity;

import com.sookmyung.concon.Order.entity.Orders;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

//    @Enumerated(EnumType.STRING)
//    private Gender gender;
//    private int age;

    private String email;
    private String password;

    // 프로필 사진
    private String profileUrl;
    private String color;

    // 알람 여부
    private boolean gift_notify;
    private boolean expiry_notify;
    private int expiry_days;

    private boolean is_verified;

//    @OneToMany(mappedBy = "sender")
//    private List<Friendship> friendship;

    @Enumerated(EnumType.STRING)
    private RoleType role;

    @OneToMany(mappedBy = "seller")
    private List<Orders> orders;

    public void updateVerifiedStatus(boolean verified) {
        is_verified = verified;
    }


    public void update(String username, String profileUrl, String color,
                       boolean gift_notify, boolean expiry_notify,
                       int expiry_days, boolean is_verified) {
        this.username = username;
        this.profileUrl = profileUrl;
        this.color = color;
        this.gift_notify = gift_notify;
        this.expiry_notify = expiry_notify;
        this.expiry_days = expiry_days;
        this.is_verified = is_verified;
    }
}
