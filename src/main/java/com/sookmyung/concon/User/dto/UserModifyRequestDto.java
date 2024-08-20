package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.Gender;
import lombok.Getter;

@Getter
public class UserModifyRequestDto {
    private String username;
    private Gender gender;
    private int age;
}
