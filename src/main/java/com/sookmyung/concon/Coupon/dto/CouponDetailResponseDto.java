package com.sookmyung.concon.Coupon.dto;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import jakarta.transaction.Transactional;
import com.sookmyung.concon.Item.repository.ItemRepository;
import com.sookmyung.concon.Coupon.repository.CouponRepository;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class CouponDetailResponseDto {

    private Long id;
    private Long userId;
    private Long itemId;
    private String barcode;

    private String imageUrl;
    private String barcodeImageUrl;

    private String name;
    private Double price;
    private LocalDate expirationDate;
    private LocalDate usedDate;
    private Boolean buyFlag;

    public static CouponDetailResponseDto toDto(Coupon coupon, String imageUrl, String barcodeImageUrl) {
        return CouponDetailResponseDto.builder()
                .id(coupon.getId())
                .userId(coupon.getUser().getId())
                .itemId(coupon.getItem().getId())
                .barcode(coupon.getBarcode())
                .imageUrl(imageUrl)
                .barcodeImageUrl(barcodeImageUrl)
                .name(coupon.getName())
                .price(coupon.getPrice())
                .expirationDate(coupon.getExpirationDate())
                .usedDate(coupon.getUsedDate())
                .buyFlag(coupon.isBuyFlag())
                .build();
    }

    @Transactional
    public CouponDetailResponseDto saveCoupon(String token, CouponCreateRequestDto request) {

        User user = findUserByToken(token);

        // 아이템 자동 생성 (필요에 따라 필드 설정)
        Item item = Item.builder()
                .name(request.getName())  // 쿠폰 이름을 아이템 이름으로 설정
                .price(request.getPrice()) // 쿠폰 가격을 아이템 가격으로 설정
                .kcal(0)  // 임시로 0 설정, 필요시 요청에서 값을 받도록 수정 가능
                .info("Default Info")  // 기본 정보, 필요시 수정 가능
                .brand("Default Brand")  // 기본 브랜드 설정
                .category("Default Category")  // 기본 카테고리 설정
                .imageUrl(request.getImageFileName())  // 쿠폰 이미지 파일을 아이템 이미지로 설정
                .build();

        // 생성된 아이템 저장
        ItemRepository.save(item);

        // 생성된 아이템을 사용하여 쿠폰 생성
        Coupon coupon = request.toEntity(user, item);

        CouponRepository.save(coupon);

        // TODO: 이미지 처리
        return CouponDetailResponseDto.toDto(coupon, null, null);
    }

}
