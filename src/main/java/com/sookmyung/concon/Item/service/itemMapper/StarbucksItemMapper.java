package com.sookmyung.concon.Item.service.itemMapper;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.service.ItemMapper;
import org.springframework.stereotype.Service;

@Service
public class StarbucksItemMapper implements ItemMapper {
    @Override
    public Item mapToUser(String[] lines) {
        return Item.builder()
                .name(lines[1])
                .imageUrl(lines[3])
                .description(lines[4])
                .brand("스타벅스")
                .category("커피, 디저트")
                .build();
    }
}
