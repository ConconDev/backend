package com.sookmyung.concon.User.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class LoginRequestDto {
    private String email;
    private String password;
}
