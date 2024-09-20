package com.sookmyung.concon.Item.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.nio.file.FileStore;

@Entity
@Table(name = "item")
@Getter
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private int kcal;

    private String info;

    private String brand;

    private String category;

    private String imageUrl;
}