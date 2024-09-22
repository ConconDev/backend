package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.RoleType;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class UserCreateRequestDto {
    private String password;
    private String email;

    public User toEntity(String password) {
        return User.builder()
                .email(email)
                .username("username")
                .password(password)
                .role(RoleType.USER)
                .color("0xff848484")
                .gift_notify(false)
                .expiry_notify(false)
                .expiry_days(7)
                .is_verified(false)
                .build();
    }
}
