package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserSimpleResponseDto {
    private Long userId;
    private String name;
    private String profileUrl;

    public static UserSimpleResponseDto toDto(User user) {
        return UserSimpleResponseDto.builder()
                .userId(user.getId())
                .name(user.getUsername())
                .profileUrl(user.getProfileUrl())
                .profileUrl(user.getProfileUrl())
                .build();
    }
}
