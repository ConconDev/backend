package com.sookmyung.concon.User.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserQRImageModifyResponseDto {
    UserIdResponseDto user;
    String QRImageModifyUrl;

    static public UserQRImageModifyResponseDto toDto(UserIdResponseDto user, String qrImageUrl) {
        return UserQRImageModifyResponseDto.builder()
                .user(user)
                .QRImageModifyUrl(qrImageUrl)
                .build();
    }
}
