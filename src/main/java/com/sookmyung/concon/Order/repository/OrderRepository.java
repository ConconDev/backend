package com.sookmyung.concon.Order.repository;

import com.sookmyung.concon.Order.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Long> {

}
