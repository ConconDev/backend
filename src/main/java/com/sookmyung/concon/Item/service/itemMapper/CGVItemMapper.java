package com.sookmyung.concon.Item.service.itemMapper;

import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.service.ItemMapper;
import org.springframework.stereotype.Service;

@Service
public class CGVItemMapper implements ItemMapper {
    @Override
    public Item mapToUser(String[] lines) {
        return Item.builder()
                .name(lines[0])
                .price(Integer.parseInt(lines[1].replace(",", "")))
                .imageUrl(lines[3])
                .description(lines[5])
                .brand("CGV")
                .category("영화, 엔터테인먼트")
                .build();
    }

}
