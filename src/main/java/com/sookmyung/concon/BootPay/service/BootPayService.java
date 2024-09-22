package com.sookmyung.concon.BootPay.service;

import com.sookmyung.concon.BootPay.dto.BootPayConfirmRequestDTO;
import com.sookmyung.concon.Order.entity.OrderStatus;
import com.sookmyung.concon.Order.entity.Orders;
import com.sookmyung.concon.Order.repository.OrderRepository;
import com.sookmyung.concon.Order.service.TransactionService;
import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.service.OrderUserFacade;
import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BootPayService {
    private final OrderUserFacade orderUserFacade;
    private final OrderRepository orderRepository;
    private final TransactionService transactionService;

    @Value("${bootpay.application-id}")
    private String applicationId;

    @Value("${bootpay.private-key}")
    private String privateKey;

    private Bootpay bootpay;

    // 부트페이 서버에서 토큰을 가져올 수 있는지 확인
    public void getBootpayToken() {
        try {
            bootpay = new Bootpay(applicationId, privateKey);
            HashMap token = bootpay.getAccessToken();
            if (token.get("error_code") != null) { // failed
                System.out.println("getAccessToken false: " + token);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 단건 조회
    public HashMap getBootpayReceipt(String receiptId) {
        try {
            getBootpayToken();
            HashMap res = bootpay.getReceipt(receiptId);
            if (res.get("error_code") == null) {
                System.out.println("getReceipt success: " + res);
            } else {
                System.out.println("getReceipt false: " + res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    // 결제 승인
    @Transactional
    public HashMap confirm(String receiptId){
        try {
            getBootpayToken();
            HashMap res = bootpay.confirm(receiptId);
            if(res.get("error_code") == null) { //success
                System.out.println("confirm success: " + res);

                // order테이블의 status column 데이터를 바꿔준다.
                Long orderId = Long.valueOf(res.get("order_id").toString());
                Optional<Orders> orderEntityOptional = orderRepository.findById(orderId);
                if(!orderEntityOptional.isPresent()){
                    System.out.println("주문 번호에 해당하는 주문 정보가 없음.");
                    return null;
                }
                transactionService.transaction(orderId);
            } else {
                System.out.println("confirm false: " + res);
            }
            return res;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    // 본인 인증
    public void certificate(String token, String receiptId) {
        User user = orderUserFacade.findUserByToken(token);

        Bootpay bootpay = new Bootpay(applicationId, privateKey);

        if (user.is_verified()) {
            System.out.println("이미 인증되었습니다. ");
            return;
        }
        try {
            getBootpayToken();

            HashMap<String, Object> res = bootpay.certificate(receiptId);

            if (res.get("error_code") == null) { //success
                System.out.println("certificate success: " + res);
                user.updateVerifiedStatus(true);
            } else {
                System.out.println("certificate false: " + res);
                throw new RuntimeException("인증 실패: " + res.get("message"));
            }
        } catch (Exception e) {
            throw new RuntimeException("인증 오류", e);
        }
    }

    @Transactional
    public String priceCheck(String token, BootPayConfirmRequestDTO request) {
        getBootpayReceipt(request.getReceiptId());
        certificate(token, request.getReceiptId());
        confirm(request.getReceiptId());
        return null;
    }
}

