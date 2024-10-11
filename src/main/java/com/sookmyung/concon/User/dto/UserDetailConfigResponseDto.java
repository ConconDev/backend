package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserDetailConfigResponseDto {
    private Long userId;
    private String username;
    private String profileUrl;
    private String QRImageUrl;
    private String color;
    private boolean gift_notify;
    private boolean expiry_notify;
    private int expiry_days;
    private boolean is_verified;

    public static UserDetailConfigResponseDto toDto(User user, String profileUrl, String QRImageUrl) {
        return UserDetailConfigResponseDto.builder()
                .userId(user.getId())
                .username(user.getUsername())
                .profileUrl(profileUrl)
                .QRImageUrl(QRImageUrl)
                .color(user.getColor())
                .gift_notify(user.isGift_notify())
                .expiry_notify(user.isExpiry_notify())
                .expiry_days(user.getExpiry_days())
                .is_verified(user.is_verified())
                .build();
    }
}
