package com.sookmyung.concon.Item.dto;

import com.sookmyung.concon.Item.Entity.Item;
import lombok.Getter;

@Getter
public class ItemCreateDto {
    private String name;
    private String description;
    private double price;
    private String brand;
    private String category;
    private String imageName;

    public Item toEntity() {
        return Item.builder()
                .name(name)
                .description(description)
                .price(price)
                .brand(brand)
                .category(category)
                .build();
    }
}
