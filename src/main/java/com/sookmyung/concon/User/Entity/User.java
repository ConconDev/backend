package com.sookmyung.concon.User.Entity;

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

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;

    private String email;
    private String password;

    // 프로필 사진
    private String profileImage;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendship;

    @Enumerated(EnumType.STRING)
    private RoleType role;


    public void update(String name, Gender gender, int age) {
        if (!this.username.equals(name)) this.username = name;
        if (this.gender != gender) this.gender = gender;
        if (this.age != age) this.age = age;
    }
}
