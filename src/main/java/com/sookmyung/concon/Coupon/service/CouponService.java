package com.sookmyung.concon.Coupon.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.Item.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private ItemRepository itemRepository;

    public Coupon saveCoupon(Long userId, String barcode, MultipartFile barcodeImage, MultipartFile image, Long itemId, String name, Double price, String expirationDateStr) {

        Item item = itemRepository.findById(itemId).orElseThrow(() -> new IllegalArgumentException("Invalid item ID"));

        Coupon coupon = new Coupon();
        coupon.setUserId(userId);
        coupon.setBarcode(barcode);

        // Barcode 및 이미지 파일 처리
        String barcodeImagePath = saveFile(barcodeImage);
        String imagePath = saveFile(image);
        coupon.setBarcodeImagePath(barcodeImagePath);
        coupon.setImagePath(imagePath);

        coupon.setItem(item);
        coupon.setName(name);
        coupon.setPrice(price);

        // 만료일자 처리
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate expirationDate = LocalDate.parse(expirationDateStr, formatter);
        coupon.setExpirationDate(expirationDate);

        // 기본 값 설정
        coupon.setUsedFlag(false);
        coupon.setBuyFlag(false);

        return couponRepository.save(coupon);
    }

    public List<Coupon> getCouponsByUserId(Long userId) {
        return couponRepository.findByUserId(userId);
    }

    public List<Coupon> getCouponsByUserIdAndUsedFlag(Long userId, Boolean usedFlag) {
        return couponRepository.findByUserIdAndUsedFlag(userId, usedFlag);
    }

    // 예시로 플래그 값을 업데이트하는 메서드
    public Coupon updateCouponStatus(Long couponId, Boolean usedFlag, Boolean buyFlag) {
        Coupon coupon = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("Invalid coupon ID"));
        coupon.setUsedFlag(usedFlag);
        coupon.setBuyFlag(buyFlag);
        return couponRepository.save(coupon);
    }

    private String saveFile(MultipartFile file) {
        return "file_path";
    }
}
