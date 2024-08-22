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
    private String password;
    private String email;
    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .username(username)
                .gender(gender)
                .age(age)
                .password(password)
                .profileImage(profileImage)
                .build();
    }
}
