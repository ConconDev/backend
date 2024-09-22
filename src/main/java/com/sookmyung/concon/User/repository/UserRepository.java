package com.sookmyung.concon.User.repository;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    boolean existsUserByEmail(String email);

    List<User> findByUsernameContaining(String keyword);

    @Query("SELECT u FROM User u " +
            "JOIN u.orders o " +
            "WHERE o.id IS NOT NULL " +
            "ORDER BY FUNCTION('RAND') ")
    List<User> findRandomUsers(Pageable pageable);

    @Query("SELECT u FROM User u " +
            "JOIN u.orders o " +
            "WHERE o.coupon.item.id = :itemId " +
            "ORDER BY FUNCTION('RAND') ")
    List<User> findRandomUsersByItem(Long itemId, Pageable pageable);
}
