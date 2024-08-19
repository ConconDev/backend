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

    private String name;

    @Enumerated(EnumType.STRING)
    private Gender gender;
    private int age;

    // 프로필 사진
    private String profileImage;

    @OneToMany(mappedBy = "user")
    private List<Friendship> friendship;



    public void update(String name, Gender gender, int age) {
        if (!this.name.equals(name)) this.name = name;
        if (this.gender != gender) this.gender = gender;
        if (this.age != age) this.age = age;
    }
}
