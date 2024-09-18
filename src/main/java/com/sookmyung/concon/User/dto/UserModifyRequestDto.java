package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.Gender;
import com.sookmyung.concon.User.Entity.User;
import lombok.Getter;

@Getter
public class UserModifyRequestDto {
    private String username;
    private String profileFilename;
    private String color;
    private boolean gift_notify;
    private boolean expiry_notify;
    private int expiry_days;
    private boolean is_verified;

    public void update(User user, String profileUrl) {
        user.update(username, profileUrl, color, gift_notify, expiry_notify, expiry_days, is_verified);
    }
}
