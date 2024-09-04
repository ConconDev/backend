package com.sookmyung.concon.User.dto;

import com.sookmyung.concon.User.Entity.Friendship;
import com.sookmyung.concon.User.Entity.FriendshipStatus;
import com.sookmyung.concon.User.Entity.User;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class FriendSimpleResponseDto {
    private Long friendshipId;
    private String name;
    private String profileUrl;
    private FriendshipStatus statue;

    public static FriendSimpleResponseDto toDto(User user, String profileUrl, Friendship friendship) {
        return FriendSimpleResponseDto.builder()
                .friendshipId(friendship.getId())
                .name(user.getUsername())
                .profileUrl(profileUrl)
                .statue(friendship.getStatus())
                .build();
    }
}
