package com.sookmyung.concon.Order.repository;

import com.sookmyung.concon.Order.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {

}
