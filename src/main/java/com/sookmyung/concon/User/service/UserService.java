package com.sookmyung.concon.User.service;

import com.sookmyung.concon.User.dto.UserDetailConfigResponseDto;
import com.sookmyung.concon.User.dto.UserDetailResponseDto;
import com.sookmyung.concon.User.dto.UserModifyRequestDto;
import com.sookmyung.concon.User.dto.UserSimpleResponseDto;

import java.util.List;

public interface UserService {

    UserDetailConfigResponseDto getUserInfo(String token);

    UserDetailResponseDto getUserInfoById(Long userId);

    List<UserSimpleResponseDto> getUsers();

    UserDetailConfigResponseDto modifyUser(String token, UserModifyRequestDto request);

    List<UserDetailResponseDto> get5RandomUser();

    List<UserDetailResponseDto> get5RandomUserByItemName(String itemName);

    void deleteUser(String token);
}
