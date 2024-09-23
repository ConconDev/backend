package com.sookmyung.concon.Item.repository;

import com.sookmyung.concon.Item.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {
    Optional<Item> findByName(String name);

    List<Item> findByNameContaining(String keyword);

    List<Item> findByCategoryContaining(String category);

    List<Item> findByCategory(String category);
}
