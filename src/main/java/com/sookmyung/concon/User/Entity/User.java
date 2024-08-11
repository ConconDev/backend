package com.sookmyung.concon.User.Entity;

import jakarta.persistence.*;

@Entity
public class User {
    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender sex;
    private int age;

    // 프로필 사진
    private String profileImage;
}
