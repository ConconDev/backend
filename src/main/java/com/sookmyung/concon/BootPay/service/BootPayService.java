package com.sookmyung.concon.BootPay.service;

import com.sookmyung.concon.User.Entity.User;
import com.sookmyung.concon.User.service.OrderUserFacade;
import kr.co.bootpay.Bootpay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class BootPayService {
    private final OrderUserFacade orderUserFacade;
    private final UserVerificationService userVerificationService;

    @Value("${bootpay.application-id}")
    private String applicationId;

    @Value("${bootpay.private-key}")
    private String privateKey;

    public HashMap<String, Object> certificate(String token, String receiptId) {
        User user = orderUserFacade.findUserByToken(token);
        if (user.is_verified()) throw new RuntimeException("이미 인증되었습니다. ");

        Bootpay bootpay = new Bootpay(applicationId, privateKey);

        try {
            bootpay.getAccessToken();

            HashMap<String, Object> res = bootpay.certificate(receiptId);

            if (res.get("error_code") == null) { //success
                System.out.println("certificate success: " + res);
                userVerificationService.updateVerifiedStatus(user);
            } else {
                System.out.println("certificate false: " + res);
                throw new RuntimeException("인증 실패: " + res.get("message"));
            }
            return res;
        } catch (Exception e) {
            throw new RuntimeException("인증 오류", e);
        }
    }
}

