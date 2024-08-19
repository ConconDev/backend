package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.Gender;
import com.sookmyung.concon.User.Entity.User;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UserCreateRequestDto {
    private String name;
    private Gender gender;
    private int age;
    private String profileImage;
    public User toEntity() {
        return User.builder()
                .name(name)
                .gender(gender)
                .age(age)
                .profileImage(profileImage)
                .build();
    }
}
