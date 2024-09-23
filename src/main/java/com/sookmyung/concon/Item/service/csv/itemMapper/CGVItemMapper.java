package com.sookmyung.concon.Item.service.csv.itemMapper;

import com.sookmyung.concon.Item.Entity.Item;
import org.springframework.stereotype.Service;

@Service
public class CGVItemMapper {
    public Item mapToUser(String[] lines) {
        return Item.builder()
                .name(lines[0])
                .price(Integer.parseInt(lines[1].replace(",", "")))
                .imageUrl(lines[6])
                .description(lines[5] + ", " + lines[2])
                .brand("CGV")
                .category("영화, 엔터테인먼트")
                .build();
    }

}
