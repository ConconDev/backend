package com.sookmyung.concon.User.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserModifyResponseDto {
    UserDetailConfigResponseDto user;
    String photoModifyUrl;

    public static UserModifyResponseDto toDto(UserDetailConfigResponseDto user, String photoModifyUrl) {
        return UserModifyResponseDto.builder()
                .user(user)
                .photoModifyUrl(photoModifyUrl)
                .build();
    }
}
