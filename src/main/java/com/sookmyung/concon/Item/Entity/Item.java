package com.sookmyung.concon.Item.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private double price;

    private String description;

    private String brand;

    private String category;

    private String imageUrl;

    private String videoName;
    private LocalDateTime videoCreatedDate;

    public void updateVideo(String videoName, LocalDateTime videoCreatedDate) {
        this.videoName = videoName;
        this.videoCreatedDate = videoCreatedDate;
    }
}
