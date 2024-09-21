package com.sookmyung.concon.Photo.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.DeleteObjectRequest;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class PhotoService {
    @Value("${AWS_S3_BUCKET}")
    private String bucket;

    private final AmazonS3 getAmazonS3Client;

    @Value("${AWS_CLOUDFRONT_DISTRIBUTE_DOMAIN}")
    private String domain;


    // http 메소드에 따른 presignedUrl 생성(get, put, delete)
    public String getPreSignedUrl(String filePath, HttpMethod httpMethod) {
        GeneratePresignedUrlRequest request =
                new GeneratePresignedUrlRequest(bucket, filePath)
                        .withMethod(httpMethod)
                        .withExpiration(getPresignedUrlExpiration());
        return getAmazonS3Client.generatePresignedUrl(request).toString();
    }

/*
    비즈니스 로직
 */
    public String createPhoto(String filePath) {
        return getPreSignedUrl(filePath, HttpMethod.PUT);
    }

    public String modifyPhoto(String oldFilePath, String newFilePath) {
        if (oldFilePath != null) {
            deletePhoto(oldFilePath);
        }
        return createPhoto(newFilePath);
    }

    public void deletePhoto(String filePath) {
        getAmazonS3Client.deleteObject(new DeleteObjectRequest(bucket, filePath));
    }

    public String getPhoto(String filePath) {
        if (filePath == null) return null;
        return domain + filePath;
    }

    private Date getPresignedUrlExpiration() {
        Date expiration = new Date();
        long expTimeMillis = expiration.getTime();
        expTimeMillis += 1000 * 60 * 2;
        expiration.setTime(expTimeMillis);
        return expiration;
    }

    public String createPath(String prefix, String fileName, LocalDateTime time) {
        return String.format("%s/%s_%s", prefix, fileName, time);
    }






}
