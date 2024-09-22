package com.sookmyung.concon.BootPay.service;

import com.sookmyung.concon.User.Entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserVerificationService {
    @Transactional
    public void updateVerifiedStatus(User user) {
        user.updateVerifiedStatus(true);
    }
}
