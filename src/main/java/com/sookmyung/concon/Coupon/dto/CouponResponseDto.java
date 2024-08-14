package com.sookmyung.concon.Coupon.dto;

import java.time.LocalDate;

public class CouponResponseDto {

    private Long id;
    private Long userId;
    private Long itemId;
    private String barcode;
    private String barcodeImagePath;
    private String imagePath;
    private String name;
    private Double price;
    private LocalDate expirationDate;
    private LocalDate usedDate;
    private Boolean usedFlag;
    private Boolean buyFlag;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getBarcodeImagePath() {
        return barcodeImagePath;
    }

    public void setBarcodeImagePath(String barcodeImagePath) {
        this.barcodeImagePath = barcodeImagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public LocalDate getUsedDate() {
        return usedDate;
    }

    public void setUsedDate(LocalDate usedDate) {
        this.usedDate = usedDate;
    }

    public Boolean getUsedFlag() {
        return usedFlag;
    }

    public void setUsedFlag(Boolean usedFlag) {
        this.usedFlag = usedFlag;
    }

    public Boolean getBuyFlag() {
        return buyFlag;
    }

    public void setBuyFlag(Boolean buyFlag) {
        this.buyFlag = buyFlag;
    }
}
