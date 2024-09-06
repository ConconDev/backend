package com.sookmyung.concon.Order.repository;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.User.Entity.User;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findBySeller(User seller, Pageable pageable);

    List<Orders> findBySellerAndStatus(User seller, OrderStatus status, Pageable pageable);

    @Query("SELECT o FROM Orders o " +
            "JOIN o.coupon c " +
            "WHERE c.item = :item " +
            "AND o.status = :status")
    List<Orders> findAllByItemAndStatus(@Param("item") Item item,
                                        @Param("status") OrderStatus status,
                                        Pageable pageable);
}
