package com.sookmyung.concon.Review.repository;

import com.sookmyung.concon.Review.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Long> {
}
