package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.Photo.dto.PhotoDto;
import com.sookmyung.concon.User.Entity.Gender;
import com.sookmyung.concon.User.Entity.User;
import lombok.Getter;

@Getter
public class UserModifyRequestDto {
    private String username;
    private String fileName;
    private String QRFileName;
    private String color;
    private boolean gift_notify;
    private boolean expiry_notify;
    private int expiry_days;
    private boolean is_verified;
}
