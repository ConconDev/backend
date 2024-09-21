package com.sookmyung.concon.Photo.service;

import com.sookmyung.concon.Coupon.Entity.Coupon;
import com.sookmyung.concon.Item.Entity.Item;
import com.sookmyung.concon.User.Entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PhotoFacade {
    private final PhotoManager photoManager;
    private final PhotoService photoService;

    private static final String USER_PREFIX = "user/";
    private static final String ITEM_PREFIX = "item/";
    private static final String COUPON_PREFIX = "coupon/";
    private static final String COUPON_BARCODE_PREFIX = "barcode:";
    private static final String BACKGROUND_PREFIX = "item/";

    public String uploadUserPhoto(User user, String imageFileName, LocalDateTime now) {
        return photoManager.postPhoto(USER_PREFIX + user.getId(), imageFileName, now);
    }

    public String uploadItemPhoto(Item item, String imageFileName) {
        return photoService.createPhoto(ITEM_PREFIX + item.getId() + "/" + imageFileName);
    }

    public String uploadCouponPhoto(Coupon coupon, String imageFileName, LocalDateTime now) {
        return photoManager.postPhoto(COUPON_PREFIX + coupon.getId(), imageFileName, now);
    }

    public String uploadCouponBarcodePhoto(Coupon coupon, String imageFileName, LocalDateTime now) {
        return photoManager.postPhoto(COUPON_PREFIX + coupon.getId() + COUPON_BARCODE_PREFIX, imageFileName, now);
    }

//    public String uploadBackgroundPhoto() {
//      return ""
//    }

    public String getUserPhotoUrl(User user) {
        return photoManager.getPhoto(USER_PREFIX + user.getId(), user.getProfilePhotoName(), user.getProfileCreatedDate());
    }
    public String getItemPhotoUrl(Item item) {
        return photoService.getPhoto(ITEM_PREFIX + item.getId() + item.getImagePath());
    }

    public String getCouponPhotoUrl(Coupon coupon) {
        return photoManager.getPhoto(COUPON_PREFIX + coupon.getId(), coupon.getImageFileName(), coupon.getImageCreateDate());
    }

    public String getCouponBarcodePhotoUrl(Coupon coupon) {
        return photoManager.getPhoto(COUPON_PREFIX + coupon.getId() + COUPON_BARCODE_PREFIX, coupon.getBarcodeImageFileName(), coupon.getBarcodeImageCreatedDate());
    }

//    public String getBackgroundPhotoUrl(User user) {}
}
