package com.sookmyung.concon.User.service;

import com.sookmyung.concon.User.dto.*;

import java.util.List;

public interface UserService {

    UserDetailConfigResponseDto getUserInfo(String token);

    UserDetailResponseDto getUserInfoById(Long userId);

    List<UserSimpleResponseDto> getUsers();

    List<UserSimpleResponseDto> getUsersByKeyword(String keyword);

    UserModifyResponseDto modifyUser(String token, UserModifyRequestDto request);

    List<UserDetailResponseDto> get5RandomUser();

    List<UserDetailResponseDto> get5RandomUserByItemName(String itemName);

    public List<UserDetailResponseDto> get5RandomUserByCategory(String category);

    void deleteUser(String token);

}
