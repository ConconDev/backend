package com.sookmyung.concon.Review.repository;

import com.sookmyung.concon.Review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long> {

    // @Query("SELECT r FROM Review r WHERE r.item.id = ?1")
    List<Review> findAllByItemId(Long itemId);

    // @Query("SELECT r FROM Review r WHERE r.user.id = ?1")
    List<Review> findAllByUserId(Long userId);

}