package com.green.fefu.chcommon;

import com.green.fefu.sms.SmsService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

import java.security.SecureRandom;

@RequiredArgsConstructor
public class SmsSender {
    private static final SecureRandom SECURE_RANDOM = new SecureRandom();

    public static String makeRandomCode() {
        return Integer.toString(SECURE_RANDOM.nextInt(900000) + 100000);
    }
}
