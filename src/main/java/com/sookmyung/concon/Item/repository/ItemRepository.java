package com.sookmyung.concon.Item.repository;

import com.sookmyung.concon.Item.Entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Long> {

}
