package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSimpleResponseDto {
    private String name;
    private String profileUrl;

    public static UserSimpleResponseDto toDto(User user, String profileUrl) {
        return UserSimpleResponseDto.builder()
                .name(user.getName())
                .profileUrl(profileUrl)
                .build();
    }
}
