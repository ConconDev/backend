package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.Gender;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserDetailResponseDto {
    private Long id;
    private String name;
    private Gender gender;
    private int age;
    private String profileUrl;

    public static UserDetailResponseDto toDto(User user, String profileUrl) {
        return UserDetailResponseDto.builder()
                .id(user.getId())
                .name(user.getName())
                .gender(user.getGender())
                .age(user.getAge())
                .profileUrl(profileUrl)
                .build();
    }
}
