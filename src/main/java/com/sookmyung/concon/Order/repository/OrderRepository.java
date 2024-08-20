package com.sookmyung.concon.Order.repository;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.User.Entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {
    List<Orders> findBySeller(User seller);

    List<Orders> findBySellerAndStatus(User seller, OrderStatus status);

    @Query("SELECT o FROM Orders o JOIN o.coupon c WHERE c.item = :item")
    List<Orders> findByItemAndStatus(@Param("item") Item item, OrderStatus status);
}
