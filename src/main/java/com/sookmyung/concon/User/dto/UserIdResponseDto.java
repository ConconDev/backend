package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class UserIdResponseDto {
    private Long userId;
    private String username;
    public static UserIdResponseDto toDto(User user) {
        return UserIdResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .build();
    }
}
