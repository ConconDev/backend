package com.sookmyung.concon.Coupon.dto;

import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;

public class CouponRequestDto {

    private Long userId;
    private String barcode;
    private MultipartFile barcodeImage;
    private MultipartFile image;
    private Long itemId;
    private String name;
    private Double price;
    private String expirationDate; // 'yyyy-MM-dd' 형식

    // Getters and Setters
    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public MultipartFile getBarcodeImage() {
        return barcodeImage;
    }

    public void setBarcodeImage(MultipartFile barcodeImage) {
        this.barcodeImage = barcodeImage;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(String expirationDate) {
        this.expirationDate = expirationDate;
    }
}
