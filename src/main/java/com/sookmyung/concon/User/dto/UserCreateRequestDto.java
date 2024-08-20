package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.Gender;
import com.sookmyung.concon.User.Entity.User;
import lombok.Getter;

import java.util.Optional;

@Getter
public class UserCreateRequestDto {
    private String username;
    private Gender gender;
    private int age;
    private String profileImage;
    public User toEntity() {
        return User.builder()
                .username(username)
                .gender(gender)
                .age(age)
                .profileImage(profileImage)
                .build();
    }
}
