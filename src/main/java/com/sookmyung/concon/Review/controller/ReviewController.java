package com.sookmyung.concon.Review.controller;

import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReviewController {
    // 후기 작성 -> redis 에 우선 캐싱 & 배치 처리로 rdb 에 저장
    // 상품 별 후기 조회
    // 내가 쓴 후기 조회
    // 후기 상세 조회
    // 후기 수정
    // 후기 삭제
}
