package com.sookmyung.concon.Item.service.csv.itemMapper;

import com.sookmyung.concon.Item.Entity.Item;


public interface ItemMapper {
    Item mapToUser(String[] lines);
}
