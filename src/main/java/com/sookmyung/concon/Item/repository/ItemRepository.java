package com.sookmyung.concon.Item.repository;

import com.sookmyung.concon.Item.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);
}