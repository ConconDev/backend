package com.sookmyung.concon.User.Entity;

import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Photo.dto.PhotoDto;
import com.sookmyung.concon.User.dto.UserModifyRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
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
    private String profilePhotoName;
    private LocalDateTime profileCreatedDate;

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


    public void update(UserModifyRequestDto request) {
        this.username = request.getUsername();
        this.color = request.getColor();
        this.gift_notify = request.isGift_notify();
        this.expiry_notify = request.isExpiry_notify();
        this.expiry_days = request.getExpiry_days();
        this.is_verified = request.is_verified();
    }



    public void updatePhoto(String fileName, LocalDateTime time) {
        this.profilePhotoName = fileName;
        this.profileCreatedDate = time;
    }
}
