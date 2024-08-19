package com.sookmyung.concon.User.repository;

import com.sookmyung.concon.User.Entity.Friendship;
import com.sookmyung.concon.User.Entity.FriendshipStatus;
import com.sookmyung.concon.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {
    List<Friendship> findByUserAndStatus(User user, FriendshipStatus status);

    List<Friendship> findByFriendAndStatus(User friend, FriendshipStatus status);

    Optional<Friendship> findByUserAndFriend(User user, User friend);
}
