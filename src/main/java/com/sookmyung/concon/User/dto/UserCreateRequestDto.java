package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.Gender;
import com.sookmyung.concon.User.Entity.RoleType;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

import java.util.Optional;

@Getter
@Builder
public class UserCreateRequestDto {
    private String username;
    private Gender gender;
    private int age;
    private String profileImageName;
    private String password;
    private String email;

    public User toEntity(String password, String profileUrl) {
        return User.builder()
                .email(email)
                .username(username)
                .gender(gender)
                .age(age)
                .password(password)
                .profileUrl(profileUrl)
                .role(RoleType.USER)
                .build();
    }
}
