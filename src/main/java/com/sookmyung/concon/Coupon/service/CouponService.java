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

        return couponRepository.save(coupon);
    }

    private String saveFile(MultipartFile file) {
        // 파일을 서버나 클라우드 스토리지에 저장하는 로직을 구현
        // 예를 들어, file.transferTo(new File("경로"));
        // 여기에 파일을 저장한 후 해당 경로를 반환
        return "file_path";
    }
}
